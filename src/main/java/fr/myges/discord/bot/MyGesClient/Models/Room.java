package fr.myges.discord.bot.MyGesClient.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private int roomId;
    private String name;
    private String floor;
    private String campus;
}
