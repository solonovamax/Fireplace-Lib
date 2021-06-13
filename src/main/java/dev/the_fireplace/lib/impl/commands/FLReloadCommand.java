package dev.the_fireplace.lib.impl.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import dev.the_fireplace.lib.api.chat.TranslatorFactory;
import dev.the_fireplace.lib.api.chat.internal.Translator;
import dev.the_fireplace.lib.api.command.FeedbackSender;
import dev.the_fireplace.lib.api.command.FeedbackSenderFactory;
import dev.the_fireplace.lib.api.command.RegisterableCommand;
import dev.the_fireplace.lib.api.command.Requirements;
import dev.the_fireplace.lib.api.storage.utility.ReloadableManager;
import dev.the_fireplace.lib.impl.FireplaceLib;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class FLReloadCommand implements RegisterableCommand {

    private final Requirements requirements;
    private final ReloadableManager reloadableManager;
    private final FeedbackSender feedbackSender;

    @Inject
    public FLReloadCommand(
        Requirements requirements,
        TranslatorFactory translatorFactory,
        FeedbackSenderFactory feedbackSenderFactory,
        ReloadableManager reloadableManager
    ) {
        Translator translator = translatorFactory.getTranslator(FireplaceLib.MODID);
        this.feedbackSender = feedbackSenderFactory.get(translator);
        this.requirements = requirements;
        this.reloadableManager = reloadableManager;
    }

    @Override
    public CommandNode<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        return commandDispatcher.register(CommandManager.literal("flreload")
            .requires(requirements::manageServer)
            .executes(this::execute)
        );
    }

    private int execute(CommandContext<ServerCommandSource> commandContext) {
        reloadableManager.reload(FireplaceLib.MODID);
        feedbackSender.basic(commandContext, "fireplacelib.command.reload.reloaded");
        return 1;
    }
}
