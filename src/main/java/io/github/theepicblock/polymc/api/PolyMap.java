/*
 * PolyMc
 * Copyright (C) 2020-2020 TheEpicBlock_TEB
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; If not, see <https://www.gnu.org/licenses>.
 */

package io.github.theepicblock.polymc.api;

import com.google.common.collect.ImmutableMap;
import io.github.theepicblock.polymc.api.item.ItemPoly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 *
 */
public class PolyMap {
    private final ImmutableMap<Item, ItemPoly> itemPolys;

    public PolyMap(ImmutableMap<Item, ItemPoly> itemPolys) {
        this.itemPolys = itemPolys;
    }

    public ItemStack getClientItem(ItemStack serverItem) {
        ItemPoly poly = itemPolys.get(serverItem.getItem());
        if (poly == null) return serverItem;

        return poly.getClientItem(serverItem);
    }

    public ImmutableMap<Item, ItemPoly> getItemPolys() {
        return itemPolys;
    }
}
