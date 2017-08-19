/*
 * Copyright 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.grzegorz2047.openguild2047;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.github.grzegorz2047.openguild.Guild;

public class Guilds {

    private Map<String, Guild> guilds = new HashMap<String, Guild>();
    private Map<UUID, Guild> mappedPlayersToGuilds = new HashMap<UUID, Guild>();
    private List<String> onlineGuilds = new ArrayList<>();


    public void guildMemberLeftServer(Player player, UUID uuid) {
        Guild guild = getPlayerGuild(uuid);
        guild.notifyGuildThatMemberLeft(player);
        verifyOnlineGuild(player, guild);
    }

    public boolean isPlayerInGuild(Player player) {
        return hasGuild(player);
    }


    public void verifyOnlineGuild(Player player, Guild guild) {
        List<String> onlineMembers = guild.getOnlineMembers();
        if (onlineMembers.size() == 0) {
            removeOnlineGuild(guild.getName());
        } else if (onlineMembers.size() == 1) {
            if (onlineMembers.contains(player.getName())) {
                removeOnlineGuild(guild.getName());
            }
        }
    }

    public Guild getPlayerGuild(UUID uuid) {
        if (this.hasGuild(uuid)) {
            return mappedPlayersToGuilds.get(uuid);
        }
        return null;
    }

    public Guild getGuild(String guildTag) {
        return guilds.get(guildTag);
    }

    public Map<String, Guild> getGuilds() {
        return guilds;
    }

    /**
     * @param uuid UUID of player, who should be checked.
     * @return boolean
     */
    public boolean hasGuild(UUID uuid) {
        return mappedPlayersToGuilds.containsKey(uuid) && mappedPlayersToGuilds.get(uuid) != null;
    }

    /**
     * @param player Player class instance, of player, who should be checked.
     * @return boolean
     */
    public boolean hasGuild(Player player) {
        return hasGuild(player.getUniqueId());
    }

    /**
     * @param tag tag of guild, for which existance should map be searched.
     * @return boolean
     */
    public boolean doesGuildExists(String tag) {
        return guilds.containsKey(tag);
    }

    public void setGuilds(Map<String, Guild> guilds) {
        this.guilds = guilds;
    }


    public void setMappedPlayersToGuilds(Map<UUID, Guild> mappedPlayersToGuilds) {
        this.mappedPlayersToGuilds = mappedPlayersToGuilds;
    }

    /**
     * @return map which contains all players, who are members of guilds.
     */
    public Map<UUID, Guild> getMappedPlayersToGuilds() {
        return mappedPlayersToGuilds;
    }

    public Guild addGuild(OpenGuild plugin, Location home, UUID owner, String tag, String description) {
        Guild guild =
                new Guild(
                        plugin,
                        tag,
                        description,
                        home,
                        owner,
                        Bukkit.getScoreboardManager().getNewScoreboard());
        guild.addMember(owner);
        guilds.put(tag, guild);
        return guild;
    }


    public boolean addOnlineGuild(String guild) {
        if (onlineGuilds.contains(guild)) {
            return false;
        }
        return this.onlineGuilds.add(guild);
    }

    public boolean removeOnlineGuild(String guild) {
        if (!onlineGuilds.contains(guild)) {
            return false;
        }
        return this.onlineGuilds.remove(guild);
    }

    public boolean isGuildOnline(String guild) {
        return onlineGuilds.contains(guild);
    }


    public void updatePlayerGuild(UUID uniqueId, Guild guild) {
        mappedPlayersToGuilds.put(uniqueId, guild);
    }

    public int getNumberOfGuilds() {
        return guilds.size();
    }

    public void addPlayer(UUID uuid, Guild playersGuild) {
        mappedPlayersToGuilds.put(uuid, playersGuild);
    }

    public List<String> getOnlineGuilds() {
        return onlineGuilds;
    }
}
