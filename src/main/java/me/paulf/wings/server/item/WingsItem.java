package me.paulf.wings.server.item;

import me.paulf.wings.server.apparatus.FlightApparatus;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import me.paulf.wings.server.sound.WingsSounds;

public class WingsItem extends ArmorItem {
    private final FlightApparatus apparatus;

    public WingsItem(Properties properties, FlightApparatus apparatus) {
        super(new WingsMaterial(), Type.CHESTPLATE, properties);
        this.apparatus = apparatus;
    }

    public FlightApparatus getApparatus() {
        return apparatus;
    }

    private static class WingsMaterial implements ArmorMaterial {
        @Override
        public int getDurabilityForType(Type type) {
            return 432; // Такая же прочность как у элитр
        }

        @Override
        public int getDefenseForType(Type type) {
            return 3;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return WingsSounds.ITEM_ARMOR_EQUIP_WINGS.get();
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Items.PHANTOM_MEMBRANE);
        }

        @Override
        public String getName() {
            return "wings";
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    }
}