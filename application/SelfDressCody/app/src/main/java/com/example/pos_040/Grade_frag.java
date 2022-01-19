package com.example.pos_040;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Grade_frag {
    private ArrayList<allele> dblist, dblist2;

    private static int[][] arr = new int[13][13];
    private static boolean[] visited;

    private ArrayList<cardModel> ailist_a = new ArrayList<>();

    public Grade_frag(ArrayList<allele> dblist, ArrayList<allele> dblist2) {
        this.dblist = dblist;
        this.dblist2 = dblist2;
    }

    public ArrayList<cardModel> calcu(int a) {

        // String[] feelfeel = new String[]{"#해맑은", "#밝은", "#기본", "#검은", "#연한", "#부드러운", "#칙칙한", "#어두운", "#아주연한", "#연한회","#회","#어두운회"};
        String[] feelfeel = new String[]{"# 캐주얼", "# 명량한", "# 선명한", "# 고급스러운", "# 부드러운", "# 온화한", "# 고풍스러운", "# 클래식", "# 따뜻한", "# 차분한", "# 도시적", "# 무게감있는"};
        // 톤인톤 코디 배열

        // 톤온톤 색 배열
        String[] feelH = new String[]{"# 활력돋아져보여","# 쾌활해보여", "# 명량해보여", "# 평온해보여", "# 성실해보여", "# 품위있어보여"};

        check();

        for (int b = 0; b < dblist2.size(); b++) {
            String tone1 = "#톤온톤 코디";
            String tone2;

            Log.e("START : ","null");
            int dif = 0;
            int asort = dblist.get(a).getSort();
            int bsort = dblist2.get(b).getSort();

            if (asort / 1000 == bsort / 1000) {
                dif = 1;                    // 봄
            }
            if ((asort % 1000) / 100 == (bsort % 1000) / 100) {
                dif = 2;                    // 여름
            }
            if ((asort % 100) / 10 == (bsort % 100) / 10) {
                dif = 3;                    // 가을
            }
            if ((asort % 10 == bsort % 10)) {
                dif = 4;                    // 겨울
            }

            int ahv = dblist.get(a).getHv();
            int bhv = dblist2.get(b).getHv();

            int[] aaa = new int[4];                 // [0] : h(1~12), [1] : S(0~9), [2]: V(0~9), [3] : 색/무채(1,0)
            int[] bbb = new int[4];
            aaa[0] = ahv/1000;
            aaa[1] = (ahv%1000)/100;
            aaa[2] = (ahv%100)/10;
            aaa[3] = ahv%10;
            bbb[0] = bhv/1000;
            bbb[1] = (bhv%1000)/100;
            bbb[2] = (bhv%100)/10;
            bbb[3] = bhv%10;

            int afeel = dblist.get(a).getFeel()+1;
            int bfeel = dblist2.get(b).getFeel()+1;


            if (dif!=0) {
                // 톤온톤
                int grade = Math.max(Math.abs(aaa[1] - bbb[1]), Math.abs(aaa[2] - bbb[2]));
                if (aaa[0] == bbb[0] && grade >= 6 && aaa[3] == 1 && bbb[3] == 1) {             // 색 - 색차 - null , 색차 - 색 - null
                    ailist_a.add(new cardModel(dblist.get(a).getImage(), dblist2.get(b).getImage(), null, dblist.get(a).getId(), dblist2.get(b).getId(), 0, dblist.get(a).getColor(), dblist2.get(b).getColor(), 0, tone1, 1));
                }
                if (aaa[3] != bbb[3]) {             // 색 - 무채 - null, 무채 - 색 - null
                    ailist_a.add(new cardModel(dblist.get(a).getImage(), dblist2.get(b).getImage(), null, dblist.get(a).getId(), dblist2.get(b).getId(), 0, dblist.get(a).getColor(), dblist2.get(b).getColor(), 0, tone1, 1));
                }

                // 톤인톤
                if (aaa[3] == 1 && bbb[3] == 1) {
                    int feel = bfs(afeel, bfeel);
                    String upfeel = feelfeel[afeel - 1] + "\n";
                    String downfeel = feelfeel[bfeel - 1] + "\n";

                    tone2 = upfeel + downfeel;
                    if (feel == 0 || feel == 1) {
                        ailist_a.add(new cardModel(dblist.get(a).getImage(), dblist2.get(b).getImage(), null, dblist.get(a).getId(), dblist2.get(b).getId(), 0, dblist.get(a).getColor(), dblist2.get(b).getColor(), 0, tone2, 1));
                    }
                }
            }

//            if (distinguish == 1 && dif == 1) {
//                // 톤온톤 코디
//                int toneH = 0;
//                String adddescrip = "\n# 악세서리를 활용해 밋밋하지 않게 해줘";
//                if (hsv_acolor[1] <= 0.02 || hsv_bcolor[1] > 0.02) {
//                    toneH = whatH(hsv_bcolor[1]);
//                }
//                if (hsv_bcolor[1] <= 0.02 || hsv_acolor[1] > 0.02) {
//                    toneH = whatH(hsv_acolor[1]);
//                } else {
//                    toneH = whatH(hsv_acolor[1]);
//                }
//
//                tone1 = feelH[toneH] + adddescrip;
//                int gradeOn = toneOn(arr, brr);
//                if (gradeOn == 0) {
//                    ailist_a.add(new cardModel(dblist.get(position).getImage(), dblist2.get(b).getImage(), null, dblist.get(position).getId(), dblist2.get(b).getId(), 0, dblist.get(position).getColor(), dblist2.get(b).getColor(), 0, tone1, 1));
//                }
//                if (gradeOn == 1) {
//                    ailist_a.add(new cardModel(dblist.get(position).getImage(), dblist2.get(b).getImage(), null, dblist.get(position).getId(), dblist2.get(b).getId(), 0, dblist.get(position).getColor(), dblist2.get(b).getColor(), 0, tone1, 2));
//                }
//                if (gradeOn == 2) {
//                    ailist_a.add(new cardModel(dblist.get(position).getImage(), dblist2.get(b).getImage(), null, dblist.get(position).getId(), dblist2.get(b).getId(), 0, dblist.get(position).getColor(), dblist2.get(b).getColor(), 0, tone1, 3));
//                }
//            }
//            if (distinguish == 2 && dif == 1) {
//                // 톤인톤 코디
//                int m = dblist.get(position).getFeel() + 1;
//                int n = dblist2.get(b).getFeel() + 1;
//                int grade = bfs(m, n);
//                //     Log.e("등급: ", String.valueOf(grade));
//                String upfeel = feelfeel[m - 1] + "\n";
//                String downfeel = feelfeel[n - 1] + "\n";
//
//                tone2 = upfeel + downfeel;
//                if (grade == 0) {
//                    ailist_a.add(new cardModel(dblist.get(position).getImage(), dblist2.get(b).getImage(), null, dblist.get(position).getId(), dblist2.get(b).getId(),0, dblist.get(position).getColor(), dblist2.get(b).getColor(), 0, tone2, 1));
//                }
//                if (grade == 1) {
//                    ailist_a.add(new cardModel(dblist.get(position).getImage(), dblist2.get(b).getImage(), null, dblist.get(position).getId(), dblist2.get(b).getId(), 0, dblist.get(position).getColor(), dblist2.get(b).getColor(), 0, tone2, 2));
//                }
//            }

        }
        Log.e("ailist", String.valueOf(ailist_a.size()));
        if(ailist_a.size()!=0) {
            return ailist_a;
        } else {
            return null;
        }
    }

    private static int bfs(int a, int b) {
        int nn = a;
        int count = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(nn);
        q.add(0);

        visited = new boolean[13];
        visited[nn] = true;

        while(!q.isEmpty() && nn!=b) {
            nn = q.poll();
            count = q.poll();
            for(int i=1; i<=12; i++) {
                if(arr[nn][i] == 1 && visited[i] == false) {
                    q.add(i);
                    q.add(count+1);
                    visited[nn] = true;
                }
            }
        }
        return count;
    }

    private static void check() {

        arr[1][2] = arr[1][3] = arr[1][4] = 1;
        arr[2][1] = arr[2][3] = arr[2][5] = arr[2][6] = 1;
        arr[3][1] = arr[3][2] = arr[3][4] = arr[3][6] = arr[3][7] = 1;
        arr[4][1] = arr[4][3] = arr[4][7] = arr[4][8] = 1;
        arr[5][2] = arr[5][6] = arr[5][9] = arr[5][10] = 1;
        arr[6][2] = arr[6][3] = arr[6][5] = arr[6][7] = arr[6][9] = arr[6][10] = arr[6][11] = 1;
        arr[7][3] = arr[7][4] = arr[7][6] = arr[7][8] = arr[7][10] = arr[7][11] = arr[7][12] = 1;
        arr[8][4] = arr[8][7] = arr[8][11] = arr[8][12] = 1;
        arr[9][5] = arr[9][6] = arr[9][10] = 1;
        arr[10][5] = arr[10][6] = arr[10][7] = arr[10][9] = arr[10][11] = 1;
        arr[11][6] = arr[11][7] = arr[11][8] = arr[11][10] = arr[11][12] = 1;
        arr[12][7] = arr[12][8] = arr[12][11] = 1;

    }

    private static int toneOn(int[] arr, int[] brr) {
        float[] hsv_a = new float[3];
        Color.RGBToHSV(arr[0], arr[1], arr[2], hsv_a);
        float[] hsv_b = new float[3];
        Color.RGBToHSV(brr[0], brr[1], brr[2], hsv_b);

        float gra=0;
        gra = Math.abs(hsv_a[2]-hsv_b[2])*100f/20;

        if(4-(gra)==-1) {
            gra = 4;
        }
        int grade = (int) gra;
        return 4-grade;
    }

    private static int tone_distinguish(int[] arr, int[] brr, int afeel, int bfeel) {
        float[] hsv_a = new float[3];
        Color.RGBToHSV(arr[0], arr[1], arr[2], hsv_a);
        float[] hsv_b = new float[3];
        Color.RGBToHSV(brr[0], brr[1], brr[2], hsv_b);

        if(hsv_a[1] <= 0.02 || hsv_b[1] <= 0.02) {
            if(hsv_a[1] <= 0.02 && hsv_b[1] <= 0.02) {
                return 0;
            } else  {
                return 1;
            }
        }
        if(whatH(hsv_a[0])==whatH(hsv_b[0]) && afeel!=bfeel) {
            return 1;       // 톤온톤
        } if(whatH(hsv_a[0])!=whatH(hsv_b[0])) {
            return 2;       // 톤인톤
        } else {
            return 0;
        }
    }
    // ["Red", "RedOrange", "Orange", "YellowOragne", "Yellow", "YellowGreen", "Green",
    // "BlueGreen", "Blue", "BlueViolet", "Violet", "RedViolet"]
    private static int whatH(float h) {
        if(h > 335 || h <= 4) {
            return 1;
        } else if(4 <= h && h < 23) {
            return 1;
        } else if(23 <= h && h < 30) {
            return 2;
        } else if(30 <= h && h < 43) {
            return 2;
        } else if(43 <= h && h < 64) {
            return 3;
        } else if(64 <= h && h < 105) {
            return 3;
        } else if(105 <= h && h < 163) {
            return 4;
        } else if(163 <= h && h < 203) {
            return 4;
        } else if(203 <= h && h < 232) {
            return 5;
        } else if(232 <= h && h < 269) {
            return 5;
        } else if(269 <= h && h < 306) {
            return 6;
        } else if(306 <= h && h < 335) {
            return 6;
        } else {
            return 0;
        }
    }
}
