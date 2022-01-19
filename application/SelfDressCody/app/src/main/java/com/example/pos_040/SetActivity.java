package com.example.pos_040;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

// 이미지 추가 설정 화면
public class SetActivity extends AppCompatActivity {

    Button btnAdd;
    Button radio_type_up, radio_type_down, radio_type_outfit;
    Button radio_spring, radio_summer, radio_autumn, radio_winter;
    ImageView imageView, btnChoose;
    RelativeLayout layoutOOO;
    final int REQUEST_CODE_GALLERY = 999;
    private String path = null;
    private int rgb;
    private int num = 1;
    private int num_season = 0;
    private int maxIdx = 0;
    private int hv = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_setting);

        init();
        GradientDrawable drawable = (GradientDrawable) getApplicationContext().getDrawable(R.drawable.background_rounding);
        imageView.setBackground(drawable);
        imageView.setClipToOutline(true);

        radio_type_up.setTextColor(Color.parseColor("#EB723D"));


        radio_type_outfit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 radio_type_outfit.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_left));
                 radio_type_outfit.setTextColor(Color.parseColor("#EB723D"));
                 radio_type_up.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_center));
                 radio_type_up.setTextColor(Color.parseColor("#111111"));
                 radio_type_down.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_center));
                 radio_type_down.setTextColor(Color.parseColor("#111111"));
                 num = 3;
                 if (num != 0 && rgb != 0 && num_season != 0) {
                     btnAdd.setBackgroundColor(Color.parseColor("#EB723D"));
                 }
             }
        });
        radio_type_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_type_down.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_left));
                radio_type_down.setTextColor(Color.parseColor("#EB723D"));
                radio_type_up.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_center));
                radio_type_up.setTextColor(Color.parseColor("#111111"));
                radio_type_outfit.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_center));
                radio_type_outfit.setTextColor(Color.parseColor("#111111"));
                num = 2;
                if (num != 0 && rgb != 0 && num_season != 0) {
                    btnAdd.setBackgroundColor(Color.parseColor("#EB723D"));
                }
            }
        });
        radio_type_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_type_up.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_left));
                radio_type_up.setTextColor(Color.parseColor("#EB723D"));
                radio_type_down.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_center));
                radio_type_down.setTextColor(Color.parseColor("#111111"));
                radio_type_outfit.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_center));
                radio_type_outfit.setTextColor(Color.parseColor("#111111"));
                num = 1;
                if(num!=0 && rgb != 0 && num_season != 0) {
                    btnAdd.setBackgroundColor(Color.parseColor("#EB723D"));
                }
            }
        });

        radio_spring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_spring.setSelected(!radio_spring.isSelected());
                if(view.isSelected()) {
                    radio_spring.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_left));
                    radio_spring.setTextColor(Color.parseColor("#EB723D"));
                    num_season += 1000;
                    if(num!=0 && rgb != 0 && num_season != 0) {
                        btnAdd.setBackgroundColor(Color.parseColor("#EB723D"));
                    }
                } else {
                    radio_spring.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_center));
                    radio_spring.setTextColor(Color.parseColor("#111111"));
                    num_season -= 1000;
                    if(num!=0 && rgb != 0 && num_season == 0) {
                        btnAdd.setBackgroundColor(Color.parseColor("#5E5D5D"));
                    }
                }
            }
        });
        radio_summer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_summer.setSelected(!radio_summer.isSelected());
                if(view.isSelected()) {
                    radio_summer.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_left));
                    radio_summer.setTextColor(Color.parseColor("#EB723D"));
                    num_season += 100;
                    if(num!=0 && rgb != 0 && num_season != 0) {
                        btnAdd.setBackgroundColor(Color.parseColor("#EB723D"));
                    }
                } else {
                    radio_summer.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_center));
                    radio_summer.setTextColor(Color.parseColor("#111111"));
                    num_season -= 100;
                    if(num!=0 && rgb != 0 && num_season == 0) {
                        btnAdd.setBackgroundColor(Color.parseColor("#5E5D5D"));
                    }
                }
            }
        });
        radio_autumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_autumn.setSelected(!radio_autumn.isSelected());
                if(view.isSelected()) {
                    radio_autumn.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_left));
                    radio_autumn.setTextColor(Color.parseColor("#EB723D"));
                    num_season += 10;
                    if(num!=0 && rgb != 0 && num_season != 0) {
                        btnAdd.setBackgroundColor(Color.parseColor("#EB723D"));
                    }
                } else {
                    radio_autumn.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_center));
                    radio_autumn.setTextColor(Color.parseColor("#111111"));
                    num_season -= 10;
                    if(num!=0 && rgb != 0 && num_season == 0) {
                        btnAdd.setBackgroundColor(Color.parseColor("#5E5D5D"));
                    }
                }
            }
        });
        radio_winter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_winter.setSelected(!radio_winter.isSelected());
                if(view.isSelected()) {
                    radio_winter.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_left));
                    radio_winter.setTextColor(Color.parseColor("#EB723D"));
                    num_season += 1;
                    if(num!=0 && rgb != 0 && num_season != 0) {
                        btnAdd.setBackgroundColor(Color.parseColor("#EB723D"));
                    }
                } else {
                    radio_winter.setBackground(getApplicationContext().getDrawable(R.drawable.button_radio_center));
                    radio_winter.setTextColor(Color.parseColor("#111111"));
                    num_season -= 1;
                    if(num!=0 && rgb != 0 && num_season == 0) {
                        btnAdd.setBackgroundColor(Color.parseColor("#5E5D5D"));
                    }
                }
            }
        });


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        SetActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });


        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                rgb = bitmap.getPixel(x, y);

                layoutOOO.setBackgroundColor(rgb);

                // color.tflite 실행
                int Red = Color.red(rgb);
                int Green = Color.green(rgb);
                int Blue = Color.blue(rgb);

                float[][] input = new float[][]{{Red},{Green},{Blue}};
                float[][] output = new float[1][13];
              //  String[] vivid = new String[]{"#해맑은", "#밝은", "#기본", "#검은", "#연한", "#부드러운", "#칙칙한", "#어두운", "#아주연한", "#연한회","#회","#어두운회"};

                Interpreter tflite = getTfliteInterpreter("rgb_wb.tflite");

                tflite.run(input, output);
//
                for(int i=0; i<13; i++) {
                    Log.e("TAG+++", String.valueOf(output[0][i]));
                    Log.e("TAG+++", String.valueOf(i));
                    if(output[0][i] > output[0][maxIdx]) {

                        maxIdx = i;
                    }
                }

                float[] hsv_color = new float[3];
                Color.RGBToHSV(Red, Green, Blue, hsv_color);
                int sgrade = (int) Math.floor(hsv_color[1]*10);    // 0~9 (S)
                int vgrade = (int) Math.floor(hsv_color[2]*10);    // 0~9 (V)
                if(sgrade==10) {
                    vgrade = 9;
                }
                if(vgrade==10) {
                    vgrade = 9;
                }
                int cn = 1;
                if(maxIdx == 12){
                    cn = 0;
                }
                hv = whatH(hsv_color[0])*1000 + sgrade*100 + vgrade*10 + cn;  // h(1~12) S(0~9) V(0~9) 색/무채(1,0)  => ex) 12881  9130
                Log.e("hvhv-=-", String.valueOf(hv));

                if(num!=0 && rgb != 0 && num_season != 0) {
                    btnAdd.setBackgroundColor(Color.parseColor("#EB723D"));
                }
//                Log.e("TAG----", String.valueOf(Red));
//                Log.e("TAG----", String.valueOf(Green));
//                Log.e("TAG----", String.valueOf(Blue));
                Log.e("TAG----", String.valueOf(maxIdx));
//                Log.e("TAG----",vivid[maxIdx]);

                

                return false;
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num != 0 && rgb != 0 && num_season != 0) {
                    try {
                        Dashboard.sqLiteHelper.insertData(
                                num,
                                path,
                                rgb,
                                num_season,
                                maxIdx,
                                hv
                        );
                        imageView.setImageResource(R.drawable.none);
                        btnAdd.setBackgroundColor(Color.parseColor("#5E5D5D"));
                        layoutOOO.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(getApplicationContext(), "옷장에 옷을 등록했습니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "선택되지 않았습니다", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "선택되지 않았습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            path = getPathFromURI(uri);
            Glide.with(this).load(uri).into(imageView);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPathFromURI(Uri contentUri) {
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();;
        return path;
    }

    // tflite
    private Interpreter getTfliteInterpreter(String modelPath) {
        try {
            return new Interpreter(loadModelFile(SetActivity.this, modelPath));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private MappedByteBuffer loadModelFile(Activity activity, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private void init() {

        radio_type_outfit = (Button) findViewById(R.id.radio_type3);
        radio_type_up = (Button) findViewById(R.id.radio_type1);
        radio_type_down = (Button) findViewById(R.id.radio_type2);
        radio_spring = (Button) findViewById(R.id.radio_spring);
        radio_summer = (Button) findViewById(R.id.radio_summer);
        radio_autumn = (Button) findViewById(R.id.radio_autumn);
        radio_winter = (Button) findViewById(R.id.radio_winter);
        btnChoose = (ImageView) findViewById(R.id.btnChoose);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        imageView = (ImageView) findViewById(R.id.imageView);
        layoutOOO = (RelativeLayout) findViewById(R.id.layout11);
        //layoutOOO = (ConstraintLayout) findViewById(R.id.layoutOOO);
    }

    private static int whatH(float h) {
        if(h > 335 || h <= 4) {
            return 1;
        } else if(4 <= h && h < 23) {
            return 2;
        } else if(23 <= h && h < 30) {
            return 3;
        } else if(30 <= h && h < 43) {
            return 4;
        } else if(43 <= h && h < 64) {
            return 5;
        } else if(64 <= h && h < 105) {
            return 6;
        } else if(105 <= h && h < 163) {
            return 7;
        } else if(163 <= h && h < 203) {
            return 8;
        } else if(203 <= h && h < 232) {
            return 9;
        } else if(232 <= h && h < 269) {
            return 10;
        } else if(269 <= h && h < 306) {
            return 11;
        } else if(306 <= h && h < 335) {
            return 12;
        } else {
            return 0;
        }
    }
}
