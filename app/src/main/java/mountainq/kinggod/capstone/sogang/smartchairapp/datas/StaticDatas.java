package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

/**
 * Created by dnay2 on 2017-03-19.
 * Static datas here is singleton
 */

public class StaticDatas {

    private static StaticDatas instance;
    public static StaticDatas getInstance(){
        return instance;
    }
    static {instance = new StaticDatas();}


}
