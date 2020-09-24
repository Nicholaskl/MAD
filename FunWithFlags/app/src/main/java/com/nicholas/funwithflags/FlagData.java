package com.nicholas.funwithflags;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

public class FlagData {
    private static List<Answer> capitalCity = Arrays.asList(new Answer[] {
            new Answer(1, "Canberra"),
            new Answer(0, "Perth"),
            new Answer(0, "Sydney")
    });

    private static List<Question> ausQues = Arrays.asList(new Question[] {
            new Question(0, 10, 5, 1, "What is the capital City", capitalCity),
            new Question(1, 20, 10, 2, "What is the capital City", capitalCity),
            new Question(0, 15, 6, 3, "What is the capital City", capitalCity)
    });

    private static List<Flag> flagList = Arrays.asList(new Flag[] {
            new Flag("Australia", R.drawable.flag_au, ausQues),
            new Flag("Bosnia", R.drawable.flag_ba, ausQues),
            new Flag("Brazil", R.drawable.flag_br, ausQues),
            new Flag("Canada", R.drawable.flag_ca, ausQues),
            new Flag("Hong Kong", R.drawable.flag_hk, ausQues),
            new Flag("Spain", R.drawable.flag_es, ausQues),
            new Flag("France", R.drawable.flag_fr, ausQues),
            new Flag("Great Britain", R.drawable.flag_gb, ausQues),
            new Flag("Georgia", R.drawable.flag_ge, ausQues),
            new Flag("Italy", R.drawable.flag_it, ausQues),
            new Flag("Japan", R.drawable.flag_jp, ausQues),
            new Flag("South Korea", R.drawable.flag_kr, ausQues),
            new Flag("Lithuania", R.drawable.flag_lt, ausQues),
            new Flag("Russia", R.drawable.flag_ru, ausQues),
            new Flag("Greece", R.drawable.flag_gr, ausQues),
            new Flag("United States of America", R.drawable.flag_us, ausQues)
    });

    private static FlagData instance = null;

    public static FlagData get()
    {
        if(instance == null)
        {
            instance = new FlagData();
        }
        return instance;
    }

    protected FlagData() {};

    public Flag get(int i)
    {
        return flagList.get(i);
    }

    public static List<Flag> getFlagList() {
        return flagList;
    }

    public int size()
    {
        return flagList.size();
    }
}
