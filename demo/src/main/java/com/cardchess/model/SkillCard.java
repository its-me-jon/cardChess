package com.cardchess.model;

import java.util.List;

public class SkillCard extends Card {
    private final Skill skill;

    public SkillCard(String displayName, String description, List<Effect> effects) {
        super(displayName);
        this.skill = new Skill(displayName, description, effects);
    }

    public Skill getSkill() {
        return skill;
    }
}
