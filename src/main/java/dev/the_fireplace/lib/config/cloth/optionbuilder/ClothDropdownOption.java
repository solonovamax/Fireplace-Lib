package dev.the_fireplace.lib.config.cloth.optionbuilder;

import dev.the_fireplace.lib.api.chat.interfaces.Translator;
import dev.the_fireplace.lib.api.client.interfaces.DropdownOptionBuilder;
import dev.the_fireplace.lib.domain.config.OptionTypeConverter;
import dev.the_fireplace.lib.entrypoints.FireplaceLib;
import me.shedaniel.clothconfig2.impl.builders.FieldBuilder;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClothDropdownOption<S, T> extends ClothGenericOption<S, T> implements DropdownOptionBuilder<S> {
    public ClothDropdownOption(Translator translator, FieldBuilder<S, ?> fieldBuilder, String optionTranslationBase, S defaultValue, Iterable<S> entries, Consumer<S> saveFunction) {
        super(translator, fieldBuilder, optionTranslationBase, defaultValue, saveFunction);
        setSelections(entries);
    }

    public ClothDropdownOption(Translator translator, FieldBuilder<T, ?> fieldBuilder, String optionTranslationBase, S defaultValue, Iterable<S> entries, Consumer<S> saveFunction, OptionTypeConverter<S, T> typeConverter) {
        super(translator, fieldBuilder, optionTranslationBase, defaultValue, saveFunction, typeConverter);
        setSelections(entries);
    }

    @Override
    public DropdownOptionBuilder<S> enableSuggestionMode() {
        try {
            Method setMaximum = findSingleParameterMethod("setSuggestionMode", Boolean.class);
            setMaximum.invoke(fieldBuilder, true);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            FireplaceLib.getLogger().error("Unable to set suggestion mode for field builder of type " + fieldBuilder.getClass(), e);
            FireplaceLib.getLogger().trace(ArrayUtils.toString(fieldBuilder.getClass().getMethods()));
        }
        return this;
    }

    private void setSelections(Iterable<S> entries) {
        List<T> selections = new ArrayList<>();
        entries.forEach(entry -> selections.add(typeConverter.convertToClothType(entry)));
        try {
            Method setErrorSupplier = findSingleParameterMethod("setSelections", Iterable.class);
            setErrorSupplier.invoke(fieldBuilder, selections);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            FireplaceLib.getLogger().error("Unable to set selections for field builder of type " + fieldBuilder.getClass(), e);
            FireplaceLib.getLogger().trace(ArrayUtils.toString(fieldBuilder.getClass().getMethods()));
        }
    }
}
