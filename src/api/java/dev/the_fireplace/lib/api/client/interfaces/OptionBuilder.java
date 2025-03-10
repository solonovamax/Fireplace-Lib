package dev.the_fireplace.lib.api.client.interfaces;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
public interface OptionBuilder<S>
{
    OptionBuilder<S> setDescriptionRowCount(byte descriptionRowCount);

    OptionBuilder<S> appendCustomDescriptionRow(Text customRow);

    OptionBuilder<S> setErrorSupplier(Function<S, Optional<Text>> errorSupplier);

    OptionBuilder<S> addDependency(OptionBuilder<Boolean> parent);

    <U> OptionBuilder<S> addDependency(OptionBuilder<U> parent, Predicate<U> dependencyMet);
}
