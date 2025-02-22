/*
 * Copyright (C) 2018 Chikachi and other contributors
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.
 */

package chikachi.discord.core;

import chikachi.discord.core.config.Configuration;
import chikachi.discord.core.config.minecraft.MinecraftConfig;
import chikachi.discord.core.config.types.MessageConfig;
import github.scarsz.discordsrv.util.DiscordUtil;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.commons.lang3.StringUtils;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DiscordClient extends ListenerAdapter {
    private static DiscordClient instance;
    private ArrayList<EventListener> eventListeners = new ArrayList<>();
    private boolean isReady = false;
    private JDA jda;

    private DiscordClient() {
    }

    public static DiscordClient getInstance() {
        if (instance == null) {
            instance = new DiscordClient();
        }

        return instance;
    }

    @Override
    public void onReady(ReadyEvent event) {
        chikachi.discord.core.DiscordIntegrationLogger.Log("Logged in as " + getSelf().getName());

        this.isReady = true;

        MinecraftConfig minecraftConfig = Configuration.getConfig().minecraft;

        // send server startup message
        DiscordClient.getInstance().broadcast(
            minecraftConfig.dimensions.generic.messages.serverStart,
            minecraftConfig.dimensions.generic.relayServerStart.getChannels(
                minecraftConfig.dimensions.generic.discordChannel
            )
        );

        this.isReady = false;
    }

    public void connect() {
        connect(false);
    }

    @SuppressWarnings("SameParameterValue")
    private void connect(boolean noMessage) {
        if (this.jda != null) {
            if (noMessage) {
                chikachi.discord.core.DiscordIntegrationLogger.Log("Is already connected", true);
            }
            return;
        }

        String token = Configuration.getConfig().discord.token;

        if (token == null || token.isEmpty()) {
            if (noMessage) {
                chikachi.discord.core.DiscordIntegrationLogger.Log("Missing token", true);
            }
            return;
        }

        JDABuilder builder = JDABuilder.createLight(token)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .setBulkDeleteSplittingEnabled(false);

        builder.addEventListeners(this);
        for (EventListener listener : eventListeners) {
            builder.addEventListeners(listener);
        }

        try {
            this.jda = builder
                .build();
        } catch (Exception e) {
            chikachi.discord.core.DiscordIntegrationLogger.Log("Failed to connect to Discord", true);
            e.printStackTrace();
        }
    }

    public void addEventListener(EventListener eventListener) {
        if (eventListener != null) {
            if (this.eventListeners.contains(eventListener)) {
                return;
            }

            this.eventListeners.add(eventListener);

            if (this.jda != null) {
                this.jda.addEventListener(eventListener);
            }
        }
    }

    public void removeEventListener(EventListener eventListener) {
        if (eventListener != null) {
            if (!this.eventListeners.contains(eventListener)) {
                return;
            }

            this.eventListeners.remove(eventListener);

            if (this.jda != null) {
                this.jda.removeEventListener(eventListener);
            }
        }
    }

    public boolean isConnected() {
        return this.jda != null && (this.isReady || this.jda.getStatus() == JDA.Status.CONNECTED);
    }

    public void disconnect() {
        disconnect(false);
    }

    void disconnect(boolean noMessage) {
        if (this.jda == null) {
            if (!noMessage) {
                chikachi.discord.core.DiscordIntegrationLogger.Log("Is already disconnected", true);
            }
            return;
        }

        this.jda.shutdown();
        if (!noMessage) {
            chikachi.discord.core.DiscordIntegrationLogger.Log("Disconnected from Discord", true);
        }
        this.jda = null;
    }

    public JDA getJda() {
        return this.jda;
    }

    public SelfUser getSelf() {
        if (this.jda == null) {
            return null;
        }

        return this.jda.getSelfUser();
    }

    public User getUser(long userId) {
        if (this.jda == null) {
            return null;
        }

        return this.jda.getUserById(userId);
    }

    void broadcast(MessageConfig message, List<Long> channels) {
        broadcast(new chikachi.discord.core.Message(message), channels);
    }

    public void broadcast(chikachi.discord.core.Message message, Long... channels) {
        broadcast(message, Arrays.asList(channels));
    }

    public void broadcast(Message message, List<Long> channels) {
        if (channels == null || channels.size() == 0 || this.jda == null || (!this.isReady && this.jda.getStatus() != JDA.Status.CONNECTED)) {
            return;
        }

        for (Long channelId : channels) {
            TextChannel channel = this.jda.getTextChannelById(channelId);
            if (channel == null) {
                chikachi.discord.core.DiscordIntegrationLogger.Log(
                    String.format(
                        "Could not find channel %s",
                        channelId
                    )
                );
            } else {
                if (!channel.canTalk()) {
                    chikachi.discord.core.DiscordIntegrationLogger.Log(
                        String.format(
                            "Missing permission to write in channel %s (%s)",
                            channel.getName(),
                            channelId
                        )
                    );
                    continue;
                }

                if (Configuration.getConfig().discord.channels.channels.containsKey(channelId)) {
                    if (!Configuration.getConfig().discord.channels.channels.get(channelId).webhook.trim().isEmpty()) {
                        chikachi.discord.core.WebhookMessage webhookMessage = message.toWebhook(channel);
                        if (webhookMessage.queue(this.jda, channelId)) {
                            continue;
                        }
                    }
                }

                String text = message.getFormattedTextDiscord(channel);

                if (text.length() > 2000) {
                    text = text.substring(0, 1997) + "...";
                }

                DiscordUtil.queueMessage(
                    channel,
                    text,
                    true
                );
            }
        }
    }

    public void setDiscordPresencePlayerCount(String[] players) {
        long count = players.length;
        String message;

        if (count == 0) {
            message = Configuration.getConfig().discord.presence.messages.noPlayerOnline;
        } else if (count == 1) {
            message = new TextFormatter()
                .addArgument("USER", players[0])
                .addArgument("COUNT", "1")
                .format(Configuration.getConfig().discord.presence.messages.onePlayerOnline);
        } else {
            message = new TextFormatter()
                .addArgument("COUNT", String.format("%d", count))
                .format(Configuration.getConfig().discord.presence.messages.morePlayersOnline);
        }
        this.setDiscordPresencePlaying(message);
    }

    public void setDiscordPresencePlaying(String message) {
        this.getJda().getPresence().setPresence(Activity.playing(message), false);
    }

    public void setDiscordPresenceWatching(String message) {
        this.getJda().getPresence().setPresence(Activity.watching(message), false);
    }

    public void updateChannelDescription(long id, String message) {
        TextChannel textChannelById = this.getJda().getTextChannelById(id);

        if (textChannelById != null) {
            try {
                //noinspection ResultOfMethodCallIgnored
                textChannelById.getManager().setTopic(message).submit().join();
            } catch (Exception e) {
                // Ignore this error.
                // This may be spammy, as it will be sent every 1 minute if the user does not fix it.
                DiscordIntegrationLogger.Log(
                    String.format(
                        "Missing permission to write in channel %s (%s)",
                        textChannelById.getName(),
                        id
                    )
                );
            }
        }
    }
}
