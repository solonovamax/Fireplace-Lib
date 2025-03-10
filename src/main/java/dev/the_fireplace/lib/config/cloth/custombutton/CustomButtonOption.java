package dev.the_fireplace.lib.config.cloth.custombutton;

import dev.the_fireplace.lib.api.chat.interfaces.Translator;
import dev.the_fireplace.lib.api.client.interfaces.CustomButtonBuilder;
import dev.the_fireplace.lib.config.cloth.optionbuilder.ClothGenericOption;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class CustomButtonOption extends ClothGenericOption<String, String> implements CustomButtonBuilder<String>
{
    public CustomButtonOption(Translator translator, CustomButtonFieldBuilder fieldBuilder, String optionTranslationBase, String defaultValue, Consumer<String> saveFunction) {
        super(translator, fieldBuilder, optionTranslationBase, defaultValue, saveFunction);
    }

    @Override
    public CustomButtonBuilder<String> setButtonTextSupplier(@Nullable Function<String, Text> buttonTextSupplier) {
        ((CustomButtonFieldBuilder) this.fieldBuilder).setButtonTextSupplier(buttonTextSupplier);
        return this;
    }
}
