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
package io.github.theepicblock.polymc.mixins.item;

import io.github.theepicblock.polymc.impl.Util;
import io.github.theepicblock.polymc.impl.mixin.ItemLocationStaticHack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xyz.nucleoid.packettweaker.PacketContext;

/**
 * This is the class responsible for replacing the serverside items with the clientside items
 */
@Mixin(targets = "net/minecraft/item/ItemStack$1")
public class ItemPolyImplementation {
    @ModifyVariable(method = "encode(Lnet/minecraft/network/RegistryByteBuf;Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private ItemStack writeItemStackHook(ItemStack itemStack) {
        var ctx = PacketContext.get();
        var map = Util.tryGetPolyMap(ctx.getClientConnection());
        return map.getClientItem(itemStack, ctx.getPlayer(), ItemLocationStaticHack.location.get());

    }
}