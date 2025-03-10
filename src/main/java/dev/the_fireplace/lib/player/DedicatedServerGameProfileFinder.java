package dev.the_fireplace.lib.player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import dev.the_fireplace.annotateddi.api.di.Implementation;
import dev.the_fireplace.lib.FireplaceLibConstants;
import dev.the_fireplace.lib.api.player.injectables.GameProfileFinder;
import dev.the_fireplace.lib.api.uuid.injectables.EmptyUUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.UserCache;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Implementation
@Environment(EnvType.SERVER)
public final class DedicatedServerGameProfileFinder implements GameProfileFinder
{
    private final EmptyUUID emptyUUID;
    private final UserCache userCache;
    private final MinecraftSessionService sessionService;
    private final Set<UUID> uuidsWithoutProfiles = new HashSet<>();
    private final Set<String> namesWithoutProfiles = new HashSet<>();

    @Inject
    public DedicatedServerGameProfileFinder(EmptyUUID emptyUUID) {
        this.emptyUUID = emptyUUID;
        MinecraftServer server = FireplaceLibConstants.getServer();
        userCache = server.getUserCache();
        sessionService = server.getSessionService();
    }

    @Override
    public Optional<GameProfile> findProfile(UUID playerId) {
        if (emptyUUID.is(playerId)) {
            return Optional.empty();
        }
        if (uuidsWithoutProfiles.contains(playerId)) {
            return Optional.empty();
        }
        Optional<GameProfile> cachedProfile = userCache.getByUuid(playerId);
        if (cachedProfile.isPresent()) {
            return cachedProfile;
        }
        GameProfile profile = new GameProfile(playerId, "");
        profile = sessionService.fillProfileProperties(profile, false);
        if (profile.getName().isEmpty()) {
            uuidsWithoutProfiles.add(playerId);
            return Optional.empty();
        } else {
            userCache.add(profile);
            return Optional.of(profile);
        }
    }

    @Override
    public Optional<GameProfile> findProfile(String playerName) {
        if (namesWithoutProfiles.contains(playerName) || playerName.isEmpty()) {
            return Optional.empty();
        }
        UserCache.setUseRemote(true);
        Optional<GameProfile> profile = userCache.findByName(playerName);
        if (profile.isEmpty()) {
            namesWithoutProfiles.add(playerName);
        }
        return profile;
    }
}
