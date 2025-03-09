package me.paulf.wings.util;

import com.google.common.collect.ImmutableListMultimap;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen; // Обновленный импорт для 1.20.1
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent; // Новый импорт для 1.20.1

public final class KeyInputListener {
    private final ImmutableListMultimap<KeyMapping, Runnable> bindings;

    private KeyInputListener(ImmutableListMultimap<KeyMapping, Runnable> bindings) {
        this.bindings = bindings;
    }

    @SubscribeEvent
    public void onKey(InputEvent.Key event) { // Обновлено для 1.20.1
        this.bindings.asMap().entrySet().stream()
                .filter(e -> e.getKey().consumeClick())
                .flatMap(e -> e.getValue().stream())
                .forEach(Runnable::run);
    }

    public static Builder builder() {
        return new BuilderRoot();
    }

    // ... остальные интерфейсы такие же как в оригинале ...

    private static final class CategoryBuilderRoot extends ChildBuilder<BuilderRoot> implements CategoryBuilder {
        private final String category;

        private CategoryBuilderRoot(BuilderRoot delegate, String category) {
            super(delegate);
            this.category = category;
        }

        @Override
        public BindingBuilder key(String desc, IKeyConflictContext context, KeyModifier modifier, int keyCode) {
            KeyMapping binding = new KeyMapping(
                    desc,
                    context,
                    modifier,
                    keyCode, // В 1.20.1 используется напрямую код клавиши
                    this.category
            );
            // В 1.20.1 регистрация происходит через событие RegisterKeyMappingsEvent
            return new BindingBuilder(this, binding);
        }
    }
}