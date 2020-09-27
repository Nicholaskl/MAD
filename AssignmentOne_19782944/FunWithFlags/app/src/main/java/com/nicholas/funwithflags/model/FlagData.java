package com.nicholas.funwithflags.model;

import com.nicholas.funwithflags.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * File: FlagData.java
 * File Created: Friday, 25th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Model class for the answer objects (contains answers to the question)
 * Reference:
 */

public class FlagData {
    //List for all the capital city questions
    private static List<Answer> capitalCityAU = Arrays.asList(
            new Answer(1, "Canberra"),
            new Answer(0, "Rio de Janeiro"),
            new Answer(0, "Tokyo"),
            new Answer(0, "Madrid")
    );
    private static List<Answer> capitalCityBA = Arrays.asList(
            new Answer(0, "Vilnius"),
            new Answer(1, "Sarajevo"),
            new Answer(0, "Brasilia"),
            new Answer(0, "Moscow")
    );
    private static List<Answer> capitalCityBR = Arrays.asList(
            new Answer(0, "Vilnius"),
            new Answer(1, "Brasilia"),
            new Answer(0, "Ottawa"),
            new Answer(0, "Sarajevo")
    );
    private static List<Answer> capitalCityCA = Arrays.asList(
            new Answer(0, "Victoria"),
            new Answer(0, "Tokyo"),
            new Answer(1, "Ottawa"),
            new Answer(0, "Brasilia")
    );
    private static List<Answer> capitalCityHK = Arrays.asList(
            new Answer(1, "Victoria"),
            new Answer(0, "Canberra"),
            new Answer(0, "Moscow"),
            new Answer(0, "Sarajevo")
    );
    private static List<Answer> capitalCityES = Arrays.asList(
            new Answer(0, "London"),
            new Answer(0, "Vilnius"),
            new Answer(1, "Madrid"),
            new Answer(0, "Tokyo")
    );
    private static List<Answer> capitalCityFR = Arrays.asList(
            new Answer(0, "Rio de Janeiro"),
            new Answer(0, "Vilnius"),
            new Answer(1, "Paris"),
            new Answer(0, "Washington D.C.")
    );
    private static List<Answer> capitalCityGB = Arrays.asList(
            new Answer(0, "Brasilia"),
            new Answer(0, "Sarajevo"),
            new Answer(0, "Ottawa"),
            new Answer(1, "London")
    );
    private static List<Answer> capitalCityGE = Arrays.asList(
            new Answer(1, "Tbilisi"),
            new Answer(0, "Seoul"),
            new Answer(0, "Sarajevo"),
            new Answer(0, "Washington D.C.")
    );
    private static List<Answer> capitalCityIT = Arrays.asList(
            new Answer(1, "Rome"),
            new Answer(0, "Vilnius"),
            new Answer(0, "Victoria"),
            new Answer(0, "Moscow")
    );
    private static List<Answer> capitalCityJP = Arrays.asList(
            new Answer(0, "Rio de Janeiro"),
            new Answer(0, "Madrid"),
            new Answer(0, "London"),
            new Answer(1, "Tokyo")
    );
    private static List<Answer> capitalCityKR = Arrays.asList(
            new Answer(0, "Victoria"),
            new Answer(1, "Seoul"),
            new Answer(0, "Paris"),
            new Answer(0, "Washington D.C.")
    );
    private static List<Answer> capitalCityLT = Arrays.asList(
            new Answer(0, "Sarajevo"),
            new Answer(0, "Ottawa"),
            new Answer(1, "Vilnius"),
            new Answer(0, "Rome")
    );
    private static List<Answer> capitalCityRU = Arrays.asList(
            new Answer(0, "Seoul"),
            new Answer(0, "London"),
            new Answer(1, "Moscow"),
            new Answer(0, "Ottawa")
    );
    private static List<Answer> capitalCityGR = Arrays.asList(
            new Answer(0, "Vilnius"),
            new Answer(0, "London"),
            new Answer(0, "Sarajevo"),
            new Answer(1, "Rio de Janeiro")
    );
    private static List<Answer> capitalCityUS = Arrays.asList(
            new Answer(0, "Vilnius"),
            new Answer(1, "Washington D.C."),
            new Answer(0, "Moscow"),
            new Answer(0, "Paris")
    );

    //The list for what flag is this
    private static List<Answer> whatFlagAU = Arrays.asList(
            new Answer(0, "Greece"),
            new Answer(0, "Lithuania"),
            new Answer(1, "Australia")
    );
    private static List<Answer> whatFlagBA = Arrays.asList(
            new Answer(1, "Bosnia"),
            new Answer(0, "Georgia"),
            new Answer(0, "Brazil")
    );
    private static List<Answer> whatFlagBR = Arrays.asList(
            new Answer(0, "South Korea"),
            new Answer(1, "Brazil"),
            new Answer(0, "Greece")
    );
    private static List<Answer> whatFlagCA = Arrays.asList(
            new Answer(0, "Spain"),
            new Answer(0, "Lithuania"),
            new Answer(1, "Canada")
    );
    private static List<Answer> whatFlagHK = Arrays.asList(
            new Answer(0, "Bosnia"),
            new Answer(1, "Hong Kong"),
            new Answer(0, "Brazil")
    );
    private static List<Answer> whatFlagES = Arrays.asList(
            new Answer(0, "Georgia"),
            new Answer(1, "Spain"),
            new Answer(0, "Great Britain")
    );
    private static List<Answer> whatFlagFR = Arrays.asList(
            new Answer(0, "Georgia"),
            new Answer(0, "Spain"),
            new Answer(1, "France")
            );
    private static List<Answer> whatFlagGB = Arrays.asList(
            new Answer(0, "South Korea"),
            new Answer(1, "Great Britain"),
            new Answer(0, "Greece")
    );
    private static List<Answer> whatFlagGE = Arrays.asList(
            new Answer(1, "Georgia"),
            new Answer(0, "United States of America"),
            new Answer(0, "South Korea")
    );
    private static List<Answer> whatFlagIT = Arrays.asList(
            new Answer(0, "Spain"),
            new Answer(1, "Italy"),
            new Answer(0, "United States of America")
    );
    private static List<Answer> whatFlagJP = Arrays.asList(
            new Answer(1, "Japan"),
            new Answer(0, "Bosnia"),
            new Answer(0, "United States of America")
    );
    private static List<Answer> whatFlagKR = Arrays.asList(
            new Answer(0, "Spain"),
            new Answer(0, "Bosnia"),
            new Answer(1, "South Korea")
    );
    private static List<Answer> whatFlagLT = Arrays.asList(
            new Answer(0, "Brazil"),
            new Answer(0, "Greece"),
            new Answer(1, "Lithuania")
    );
    private static List<Answer> whatFlagRU = Arrays.asList(
            new Answer(0, "United States of America"),
            new Answer(0, "Australia"),
            new Answer(1, "Russia")
    );
    private static List<Answer> whatFlagGR = Arrays.asList(
            new Answer(1, "Greece"),
            new Answer(0, "Japan"),
            new Answer(0, "Georgia")
    );
    private static List<Answer> whatFlagUS = Arrays.asList(
            new Answer(0, "Japan"),
            new Answer(1, "United States of America"),
            new Answer(0, "Lithuania")
    );

    //List for population of current flag
    private static List<Answer> populationAU = Arrays.asList(
            new Answer(0, "209.5 million"),
            new Answer(1, "24.99 Million")
    );
    private static List<Answer> populationBA = Arrays.asList(
            new Answer(1, "3.324 Million"),
            new Answer(0, "209.5 million")
    );
    private static List<Answer> populationBR = Arrays.asList(
            new Answer(0, "60.36 million"),
            new Answer(1, "209.5 million")
    );
    private static List<Answer> populationCA = Arrays.asList(
            new Answer(0, "2.794 million"),
            new Answer(1, "37.59 million")
    );
    private static List<Answer> populationHK = Arrays.asList(
            new Answer(1, "7.451 million"),
            new Answer(0, "144.5 million")
    );
    private static List<Answer> populationES = Arrays.asList(
            new Answer(0, "3.324 Million"),
            new Answer(1, "46.94 million")
    );
    private static List<Answer> populationFR = Arrays.asList(
            new Answer(1, "66.99 million"),
            new Answer(0, "126.5 million")
    );
    private static List<Answer> populationGB = Arrays.asList(
            new Answer(1, "66.65 million"),
            new Answer(0, "24.99 Million")
    );
    private static List<Answer> populationGE = Arrays.asList(
            new Answer(0, "7.451 million"),
            new Answer(1, "3.731 million")
    );
    private static List<Answer> populationIT = Arrays.asList(
            new Answer(0, "66.65 million"),
            new Answer(1, "60.36 million")
    );
    private static List<Answer> populationJP = Arrays.asList(
            new Answer(1, "126.5 million"),
            new Answer(0, "144.5 million")
    );
    private static List<Answer> populationKR = Arrays.asList(
            new Answer(1, "51.64 million"),
            new Answer(0, "66.99 million")
    );
    private static List<Answer> populationLT = Arrays.asList(
            new Answer(0, "37.59 million"),
            new Answer(1, "2.794 million")
    );
    private static List<Answer> populationRU = Arrays.asList(
            new Answer(0, "66.65 million"),
            new Answer(1, "144.5 million")
    );
    private static List<Answer> populationGR = Arrays.asList(
            new Answer(1, "10.72 million"),
            new Answer(0, "144.5 million")
    );
    private static List<Answer> populationUS = Arrays.asList(
            new Answer(0, "10.72 million"),
            new Answer(1, "328.2 million")
    );

    //List for continent of current flag question
    private static List<Answer> continentAU = Arrays.asList(
            new Answer(0, "Asia"),
            new Answer(1, "Oceania")
    );
    private static List<Answer> continentBA = Arrays.asList(
            new Answer(1, "Europe"),
            new Answer(0, "Oceania")
    );
    private static List<Answer> continentBR = Arrays.asList(
            new Answer(0, "Asia"),
            new Answer(1, "South America")
    );
    private static List<Answer> continentCA = Arrays.asList(
            new Answer(0, "Europe"),
            new Answer(1, "North America")
    );
    private static List<Answer> continentHK = Arrays.asList(
            new Answer(1, "Asia"),
            new Answer(0, "Europe")
    );
    private static List<Answer> continentES = Arrays.asList(
            new Answer(0, "Asia"),
            new Answer(1, "Europe")
    );
    private static List<Answer> continentFR = Arrays.asList(
            new Answer(1, "Europe"),
            new Answer(0, "South America")
    );
    private static List<Answer> continentGB = Arrays.asList(
            new Answer(1, "Europe"),
            new Answer(0, "Asia")
    );
    private static List<Answer> continentGE = Arrays.asList(
            new Answer(0, "North America"),
            new Answer(1, "Europe")
    );
    private static List<Answer> continentIT = Arrays.asList(
            new Answer(0, "South America"),
            new Answer(1, "Europe")
    );
    private static List<Answer> continentJP = Arrays.asList(
            new Answer(0, "Europe"),
            new Answer(1, "Asia")
    );
    private static List<Answer> continentKR = Arrays.asList(
            new Answer(1, "Asia"),
            new Answer(0, "North America")
    );
    private static List<Answer> continentLT = Arrays.asList(
            new Answer(1, "Europe"),
            new Answer(0, "Oceania")
    );
    private static List<Answer> continentRU = Arrays.asList(
            new Answer(1, "Asia"),
            new Answer(0, "North America")
    );
    private static List<Answer> continentGR = Arrays.asList(
            new Answer(0, "Oceania"),
            new Answer(1, "Europe")
    );
    private static List<Answer> continentUS = Arrays.asList(
            new Answer(0, "Oceania"),
            new Answer(1, "North America")
    );

    //List for dominant religion of current country
    private static List<Answer> religionAU = Arrays.asList(
            new Answer(0, "Catholic Christian"),
            new Answer(1, "Christian")
    );
    private static List<Answer> religionBA = Arrays.asList(
            new Answer(0, "Eastern Orthodox Christian"),
            new Answer(1, "Muslim")
    );
    private static List<Answer> religionBR = Arrays.asList(
            new Answer(1, "Christian"),
            new Answer(0, "Eastern Orthodox Christian")
    );
    private static List<Answer> religionCA = Arrays.asList(
            new Answer(1, "Christian"),
            new Answer(0, "Russian Orthodox Christian")
    );
    private static List<Answer> religionHK = Arrays.asList(
            new Answer(0, "Catholic Christian"),
            new Answer(1, "Other/None")
    );
    private static List<Answer> religionES = Arrays.asList(
            new Answer(0, "Russian Orthodox Christian"),
            new Answer(1, "Catholic Christian")
    );
    private static List<Answer> religionFR = Arrays.asList(
            new Answer(0, "Eastern Orthodox Christian"),
            new Answer(1, "Catholic Christian")
    );
    private static List<Answer> religionGB = Arrays.asList(
            new Answer(0, "Eastern Orthodox Christian"),
            new Answer(1, "Christian")
    );
    private static List<Answer> religionGE = Arrays.asList(
            new Answer(1, "Eastern Orthodox Christian"),
            new Answer(0, "Catholic Christian")
    );
    private static List<Answer> religionIT = Arrays.asList(
            new Answer(1, "Catholic Christian"),
            new Answer(0, "Christian")
    );
    private static List<Answer> religionJP = Arrays.asList(
            new Answer(0, "Christian"),
            new Answer(1, "Shintoism")
    );
    private static List<Answer> religionKR = Arrays.asList(
            new Answer(0, "Christian"),
            new Answer(1, "None")
    );
    private static List<Answer> religionLT = Arrays.asList(
            new Answer(1, "Christian"),
            new Answer(0, "Eastern Orthodox Christian")
    );
    private static List<Answer> religionRU = Arrays.asList(
            new Answer(0, "Other/None"),
            new Answer(1, "Russian Orthodox Christian")
    );
    private static List<Answer> religionGR = Arrays.asList(
            new Answer(0, "Christian"),
            new Answer(1, "Eastern Orthodox Christian")
    );
    private static List<Answer> religionUS = Arrays.asList(
            new Answer(0, "Catholic Christian"),
            new Answer(1, "Protestantism")
    );

    //Special Questions
    private static List<Answer> sydneyOp = Arrays.asList(
            new Answer(0, "Hobart"),
            new Answer(0, "Perth"),
            new Answer(1, "Sydney")
    );
    private static List<Answer> animalAus = Arrays.asList(
            new Answer(0, "Kangaroo"),
            new Answer(1, "Tiger"),
            new Answer(0, "Quokka"),
            new Answer(0, "Koala")
    );
    private static List<Answer> bellTower = Arrays.asList(
            new Answer(0, "Hobart"),
            new Answer(1, "Perth"),
            new Answer(0, "Sydney")
    );
    private static List<Answer> highestPopAus = Arrays.asList(
            new Answer(0, "Melbourne"),
            new Answer(1, "Sydney"),
            new Answer(0, "Canberra"),
            new Answer(0, "Brisbane")
    );
    private static List<Answer> australiaZoo = Arrays.asList(
            new Answer(0, "Melbourne"),
            new Answer(1, "Brisbane"),
            new Answer(0, "Canberra"),
            new Answer(0, "Hobart")
    );
    private static List<Answer> eiffelTower = Arrays.asList(
            new Answer(0, "Versailles"),
            new Answer(1, "Paris"),
            new Answer(0, "avignon")
    );
    private static List<Answer> colloseum = Arrays.asList(
            new Answer(0, "Venice"),
            new Answer(0, "Vatican City"),
            new Answer(1, "Rome")
    );
    private static List<Answer> foodIT = Arrays.asList(
            new Answer(1, "Pizza"),
            new Answer(0, "Katsu Chicken"),
            new Answer(0, "Hot dogs")
    );
    private static List<Answer> leaningTower = Arrays.asList(
            new Answer(1, "Pisa"),
            new Answer(0, "Rome"),
            new Answer(0, "Milan")
    );
    private static List<Answer> languageIT = Arrays.asList(
            new Answer(0, "Italyian"),
            new Answer(1, "Italian")
    );
    private static List<Answer> shard = Arrays.asList(
            new Answer(0, "Oxford"),
            new Answer(0, "Manchester"),
            new Answer(1, "London")
    );
    private static List<Answer> smallestNation = Arrays.asList(
            new Answer(0, "Ireland"),
            new Answer(0, "Scottland"),
            new Answer(1, "England")
    );
    private static List<Answer> whoQueen = Arrays.asList(
            new Answer(1, "Elizabeth II"),
            new Answer(0, "Victoria II"),
            new Answer(0, "Elizabeth I")
    );
    private static List<Answer> currentPresident = Arrays.asList(
            new Answer(1, "Donald Trump"),
            new Answer(0, "Obama"),
            new Answer(0, "Hillary Clinton")
    );
    private static List<Answer> hamilton = Arrays.asList(
            new Answer(0, "Peggy"),
            new Answer(0, "Jefferson"),
            new Answer(1, "Alexander")
    );

    //List for each of the whole questions
    private static List<Question> quesAU = Arrays.asList(
            new Question(0, 8, 2, 1, "What is the capital City?", capitalCityAU),
            new Question(0, 4, 6, 2, "What flag is this?", whatFlagAU),
            new Question(0, 7, 7, 3, "What is the population?", populationAU),
            new Question(0, 9, 8, 4, "What Continent is this on?", continentAU),
            new Question(0, 5, 5, 5, "What is the major religion?", religionAU),
            new Question(1, 6, 6, 6, "Where is the opera house?", sydneyOp),
            new Question(1, 3, 4, 7, "Which animal is not from this country?", animalAus),
            new Question(0, 7, 6, 8, "Where is the bell tower?", bellTower),
            new Question(0, 2, 2, 9, "Which city has the highest population?", highestPopAus),
            new Question(0, 5, 4, 10, "Where is Australia zoo?", australiaZoo)
    );
    private static List<Question> quesBA = Arrays.asList(
            new Question(0, 4, 7, 1, "What is the capital City?", capitalCityBA),
            new Question(0, 7, 5, 2, "What flag is this?", whatFlagBA),
            new Question(1, 4, 2, 3, "What is the population?", populationBA),
            new Question(0, 6, 7, 4, "What Continent is this on?", continentBA),
            new Question(0, 5, 6, 5, "What is the major religion?", religionBA)
    );
    private static List<Question> quesBR = Arrays.asList(
            new Question(0, 7, 3, 1, "What is the capital City?", capitalCityBR),
            new Question(0, 1, 3, 2, "What flag is this?", whatFlagBR),
            new Question(0, 7, 4, 3, "What is the population?", populationBR),
            new Question(0, 5, 3, 4, "What Continent is this on?", continentBR),
            new Question(0, 3, 7, 5, "What is the major religion?", religionBR)
    );
    private static List<Question> quesCA = Arrays.asList(
            new Question(0, 10, 8, 1, "What is the capital City?", capitalCityCA),
            new Question(1, 1, 1, 2, "What flag is this?", whatFlagCA),
            new Question(0, 3, 1, 3, "What is the population?", populationCA),
            new Question(0, 6, 3, 4, "What Continent is this on?", continentCA),
            new Question(0, 7, 1, 5, "What is the major religion?", religionCA)
    );
    private static List<Question> quesHK = Arrays.asList(
            new Question(0, 3, 6, 1, "What is the capital City?", capitalCityHK),
            new Question(0, 9, 8, 2, "What flag is this?", whatFlagHK),
            new Question(0, 10, 7, 3, "What is the population?", populationHK),
            new Question(0, 6, 1, 4, "What Continent is this on?", continentHK),
            new Question(0, 9, 6, 5, "What is the major religion?", religionHK)
    );
    private static List<Question> quesES = Arrays.asList(
            new Question(0, 8, 4, 1, "What is the capital City?", capitalCityES),
            new Question(0, 4, 7, 2, "What flag is this?", whatFlagES),
            new Question(0, 7, 4, 3, "What is the population?", populationES),
            new Question(1, 9, 5, 4, "What Continent is this on?", continentES),
            new Question(0, 8, 2, 5, "What is the major religion?", religionES)
    );
    private static List<Question> quesFR = Arrays.asList(
            new Question(0, 7, 4, 1, "What is the capital City?", capitalCityFR),
            new Question(0, 6, 2, 2, "What flag is this?", whatFlagFR),
            new Question(0, 4, 2, 3, "What is the population?", populationFR),
            new Question(0, 7, 3, 4, "What Continent is this on?", continentFR),
            new Question(0, 3, 8, 5, "What is the major religion?", religionFR),
            new Question(1, 5, 4, 6, "Where is the eiffel tower?", eiffelTower)
    );
    private static List<Question> quesGB = Arrays.asList(
            new Question(0, 7, 6, 1, "What is the capital City?", capitalCityGB),
            new Question(0, 7, 2, 2, "What flag is this?", whatFlagGB),
            new Question(0, 9, 8, 3, "What is the population?", populationGB),
            new Question(0, 4, 2, 4, "What Continent is this on?", continentGB),
            new Question(0, 9, 7, 5, "What is the major religion?", religionGB),
            new Question(1, 5, 6, 6, "Where is the shard?", shard),
            new Question(0, 3, 5, 7, "What is the smallest nation?", smallestNation),
            new Question(0, 2, 1, 8, "Who is the current queen", whoQueen)
    );
    private static List<Question> quesGE = Arrays.asList(
            new Question(0, 8, 8, 1, "What is the capital City?", capitalCityGE),
            new Question(0, 4, 3, 2, "What flag is this?", whatFlagGE),
            new Question(1, 10, 5, 3, "What is the population?", populationGE),
            new Question(0, 8, 3, 4, "What Continent is this on?", continentGE),
            new Question(0, 7, 7, 5, "What is the major religion?", religionGE)
    );
    private static List<Question> quesIT = Arrays.asList(
            new Question(0, 10, 7, 1, "What is the capital City?", capitalCityIT),
            new Question(0, 10, 7, 2, "What flag is this?", whatFlagIT),
            new Question(0, 5, 1, 3, "What is the population?", populationIT),
            new Question(0, 2, 5, 4, "What Continent is this on?", continentIT),
            new Question(0, 9, 10, 5, "What is the major religion?", religionIT),
            new Question(0, 1, 5, 6, "What is a found from this country?", foodIT),
            new Question(1, 9, 8, 7, "Where is the colloseum", colloseum),
            new Question(0, 3, 8, 8, "Where is the leaning tower?", leaningTower),
            new Question(0, 1, 10, 9, "What language do they speak here?", languageIT)
    );
    private static List<Question> quesJP = Arrays.asList(
            new Question(0, 6, 1, 1, "What is the capital City?", capitalCityJP),
            new Question(0, 2, 5, 2, "What flag is this?", whatFlagJP),
            new Question(0, 10, 7, 3, "What is the population?", populationJP),
            new Question(0, 9, 4, 4, "What Continent is this on?", continentJP),
            new Question(0, 5, 3, 5, "What is the major religion?", religionJP)
    );
    private static List<Question> quesKR = Arrays.asList(
            new Question(0, 4, 7, 1, "What is the capital City?", capitalCityKR),
            new Question(0, 6, 1, 2, "What flag is this?", whatFlagKR),
            new Question(1, 1, 3, 3, "What is the population?", populationKR),
            new Question(0, 2, 6, 4, "What Continent is this on?", continentKR),
            new Question(0, 10, 6, 5, "What is the major religion?", religionKR)
    );
    private static List<Question> quesLT = Arrays.asList(
            new Question(0, 8, 5, 1, "What is the capital City?", capitalCityLT),
            new Question(0, 5, 8, 2, "What flag is this?", whatFlagLT),
            new Question(0, 6, 2, 3, "What is the population?", populationLT),
            new Question(0, 3, 8, 4, "What Continent is this on?", continentLT),
            new Question(0, 6, 5, 5, "What is the major religion?", religionLT)
    );
    private static List<Question> quesRU = Arrays.asList(
            new Question(0, 2, 8, 1, "What is the capital City?", capitalCityRU),
            new Question(0, 2, 6, 2, "What flag is this?", whatFlagRU),
            new Question(0, 6, 4, 3, "What is the population?", populationRU),
            new Question(0, 8, 2, 4, "What Continent is this on?", continentRU),
            new Question(0, 3, 4, 5, "What is the major religion?", religionRU)
    );
    private static List<Question> quesGR = Arrays.asList(
            new Question(0, 3, 3, 1, "What is the capital City?", capitalCityGR),
            new Question(0, 2, 5, 2, "What flag is this?", whatFlagGR),
            new Question(0, 7, 3, 3, "What is the population?", populationGR),
            new Question(0, 6, 2, 4, "What Continent is this on?", continentGR),
            new Question(0, 8, 4, 5, "What is the major religion?", religionGR)
    );
    private static List<Question> quesUS = Arrays.asList(
            new Question(0, 1, 2, 1, "What is the capital City?", capitalCityUS),
            new Question(0, 6, 7, 2, "What flag is this?", whatFlagUS),
            new Question(0, 10, 7, 3, "What is the population?", populationUS),
            new Question(0, 9, 7, 4, "What Continent is this on?", continentUS),
            new Question(1, 5, 20, 5, "What is hamilton's first name?", hamilton),
            new Question(0, 6, 5, 6, "Who is the current president", currentPresident),
            new Question(0, 7, 7, 7, "What is the major religion?", religionUS)
    );

    //List for flags
    private static List<Flag> flagList = Arrays.asList(
            new Flag("Australia", R.drawable.flag_au, quesAU),
            new Flag("Bosnia", R.drawable.flag_ba, quesBA),
            new Flag("Brazil", R.drawable.flag_br, quesBR),
            new Flag("Canada", R.drawable.flag_ca, quesCA),
            new Flag("Hong Kong", R.drawable.flag_hk, quesHK),
            new Flag("Spain", R.drawable.flag_es, quesES),
            new Flag("France", R.drawable.flag_fr, quesFR),
            new Flag("Great Britain", R.drawable.flag_gb, quesGB),
            new Flag("Georgia", R.drawable.flag_ge, quesGE),
            new Flag("Italy", R.drawable.flag_it, quesIT),
            new Flag("Japan", R.drawable.flag_jp, quesJP),
            new Flag("South Korea", R.drawable.flag_kr, quesKR),
            new Flag("Lithuania", R.drawable.flag_lt, quesLT),
            new Flag("Russia", R.drawable.flag_ru, quesRU),
            new Flag("Greece", R.drawable.flag_gr, quesGR),
            new Flag("United States of America", R.drawable.flag_us, quesUS)
    );

    protected FlagData() {};

    public static List<Flag> getFlagList() {
        return flagList;
    }
}
