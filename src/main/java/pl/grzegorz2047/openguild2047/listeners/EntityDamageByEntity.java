/*
 * The MIT License
 *
 * Copyright 2014 Grzegorz.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.grzegorz2047.openguild2047.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.grzegorz2047.openguild2047.GenConf;
import pl.grzegorz2047.openguild2047.GuildHelper;
import pl.grzegorz2047.openguild2047.OpenGuild;
import pl.grzegorz2047.openguild2047.SimpleGuild;
import pl.grzegorz2047.openguild2047.managers.MsgManager;

/**
 *
 * @author Grzegorz
 */
public class EntityDamageByEntity implements Listener {
    private OpenGuild plugin;
    
    public EntityDamageByEntity(OpenGuild plugin) {
        this.plugin = plugin;
    }
    

    @EventHandler
    public void onSomeoneAttack(EntityDamageByEntityEvent e) {
        if(e.isCancelled()) {
            return;
        }
        if(GenConf.teampvp) {
            return;
        }
        //Jezeli atakowali sie lukiem czy czymkolwiek ludzie z wlasnej gildii to zablokuj
        //Daj tu też opcję, że jak jest on to można siebie nawalać
        Entity attacker = (Entity) e.getDamager();
        Entity attacked = (Entity) e.getEntity();
        //System.out.println("atakuja!");
        if(attacker instanceof Player && attacked instanceof Player) {
            Player attackerp = (Player) attacker;
            Player attackedp = (Player) attacked;
            if(plugin.getGuildHelper().hasGuild(attackerp.getUniqueId()) && plugin.getGuildHelper().hasGuild(attackedp.getUniqueId())) {
                SimpleGuild sg = plugin.getGuildHelper().getPlayerGuild(attackerp.getUniqueId());
                if(sg.containsMember(attackerp.getUniqueId()) 
                    && sg.containsMember(attackedp.getUniqueId())) {
                    e.setCancelled(true);
                    if(GenConf.TEAMPVP_MSG) {
                        attackedp.sendMessage(MsgManager.get("pvpguildmember", "&cNie mozesz uderzyc gracza sojuszniczej gildii"));
                    }
                }
            }

        }
        else if (attacker instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();
            if(arrow.getShooter() instanceof Player && e.getEntity() instanceof Player) {
                Player attackerp = (Player) arrow.getShooter();
                Player attackedp = (Player) e.getEntity();
                if(plugin.getGuildHelper().hasGuild(attackerp.getUniqueId()) && plugin.getGuildHelper().hasGuild(attackedp.getUniqueId())) {
                    SimpleGuild sg = plugin.getGuildHelper().getPlayerGuild(attackerp.getUniqueId());
                    if(sg.containsMember(attackerp.getUniqueId()) 
                        && sg.containsMember(attackedp.getUniqueId())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
