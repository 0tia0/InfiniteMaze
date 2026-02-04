package me.mattia.maze.messages;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.mattia.maze.InfiniteMaze;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class TextFormatter {

    private final InfiniteMaze infiniteMaze;

    public TextComponent formatText(String message, Player player) {
        if (message == null) message = "";
        message = basicFormat(message);
        message = PlaceholderAPI.setPlaceholders(player, message);

        return parseLinks(message);
    }

    public TextComponent formatText(String message) {
        if (message == null) message = "";
        message = basicFormat(message);
        return parseLinks(message);
    }

    public String basicFormat(String message) {
        String prefix = infiniteMaze.getConfigs()
                .getMessagesConfig()
                .getConfig()
                .getString("prefix", "&aInfiniteMaze &8&l»&r");

        message = message.replace("%prefix%", prefix);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private TextComponent parseLinks(String message) {
        TextComponent result = new TextComponent();

        Pattern pattern = Pattern.compile("\\[(.+?)\\]\\((https?://\\S+?)\\)");
        Matcher matcher = pattern.matcher(message);

        int lastIndex = 0;
        while (matcher.find()) {
            if (matcher.start() > lastIndex) {
                String before = message.substring(lastIndex, matcher.start());
                result.addExtra(new TextComponent(before));
            }

            String linkText = matcher.group(1);
            String url = matcher.group(2);

            TextComponent link = new TextComponent(linkText);
            link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
            result.addExtra(link);

            lastIndex = matcher.end();
        }

        if (lastIndex < message.length()) {
            result.addExtra(new TextComponent(message.substring(lastIndex)));
        }

        return result;
    }
}