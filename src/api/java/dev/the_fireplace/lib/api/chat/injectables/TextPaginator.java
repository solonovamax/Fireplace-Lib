package dev.the_fireplace.lib.api.chat.injectables;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.List;

public interface TextPaginator
{
    void sendPaginatedChat(ServerCommandSource targetCommandSource, String switchPageCommand, List<? extends Text> allItems, int pageIndex);
}
