package fr.myges.discord.api.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Discipline {
    private String name;
    private String teacher;
    private String student_group_name;
    private int teacher_id;
    private int trimester_id;
}
