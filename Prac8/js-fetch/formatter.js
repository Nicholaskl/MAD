"use strict";
class Entry {
    constructor(name, strength, relationship) {
        this.name = name;
        this.strength = strength;
        this.relationship = relationship;
    }
    format() {
        return this.name + ": " + this.relationship + " (" + this.strength + ")";
    }
}
function validRelationship(rel) {
    return ["ally", "neutral", "enemy"].indexOf(rel) != -1;
}
function formatData(rawData) {
    let formatted = "";
    for (let i = 0; i < rawData.factions.length; i++) {
        let faction = rawData.factions[i];
        if (validRelationship(faction.relationship)) {
            let entry = new Entry(faction.name, faction.strength, faction.relationship);
            formatted += entry.format() + "\n";
        }
        else {
            throw new Error("Unknown relationship type: '" + faction.relationship + "'");
        }
    }
    return formatted;
}