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

package chikachi.discord.core.config.minecraft;

import chikachi.discord.core.config.minecraft.MinecraftIntegrationConfig;
import chikachi.discord.core.config.minecraft.MinecraftMainDimensionConfig;
import com.google.gson.annotations.Since;

public class MinecraftConfig {
    @Since(3.0)
    public chikachi.discord.core.config.minecraft.MinecraftMainDimensionConfig dimensions = new chikachi.discord.core.config.minecraft.MinecraftMainDimensionConfig();
    @Since(3.0)
    public chikachi.discord.core.config.minecraft.MinecraftIntegrationConfig integrations = new chikachi.discord.core.config.minecraft.MinecraftIntegrationConfig();

    public void fillFields() {
        if (this.dimensions == null) {
            this.dimensions = new MinecraftMainDimensionConfig();
        }
        this.dimensions.fillFields();

        if (this.integrations == null) {
            this.integrations = new MinecraftIntegrationConfig();
        }
    }
}
