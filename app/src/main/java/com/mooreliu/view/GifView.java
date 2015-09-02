package com.mooreliu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mooreliu.R;

/**
 * Created by liuyi on 15/9/2.
 */
public class GifView extends ImageView{

        private boolean isGifImage;
        private int image;
        private Movie movie;
        private long movieStart = 0;

        public GifView(Context context, AttributeSet attrs) {
            super(context, attrs);
            //»ñÈ¡×Ô¶¨ÒåÊôÐÔisgifimage
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GifView);
            isGifImage = array.getBoolean(R.styleable.GifView_isgifimage, true);
            array.recycle();//»ñÈ¡×Ô¶¨ÒåÊôÐÔÍê±ÏºóÐèÒªrecycle£¬²»È»»á¶ÔÏÂ´Î»ñÈ¡Ôì³ÉÓ°Ïì
            //»ñÈ¡ImageViewµÄÄ¬ÈÏsrcÊôÐÔ
            image = attrs.getAttributeResourceValue( "http://schemas.android.com/apk/res/android", "src", 0);

            movie = Movie.decodeStream(getResources().openRawResource(image));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);//Ö´ÐÐ¸¸ÀàonDraw·½·¨£¬»æÖÆ·ÇgifµÄ×ÊÔ´
            if(isGifImage){//ÈôÎªgifÎÄ¼þ£¬Ö´ÐÐDrawGifImage()£¬Ä¬ÈÏÖ´ÐÐ
                DrawGifImage(canvas);
            }
        }

        private void DrawGifImage(Canvas canvas) {
            //»ñÈ¡ÏµÍ³µ±Ç°Ê±¼ä
            long nowTime = android.os.SystemClock.currentThreadTimeMillis();
            if(movieStart == 0){
                //ÈôÎªµÚÒ»´Î¼ÓÔØ£¬¿ªÊ¼Ê±¼äÖÃÎªnowTime
                movieStart = nowTime;
            }
            if(movie != null){//ÈÝ´í´¦Àí
                int duration = movie.duration();//»ñÈ¡gif³ÖÐøÊ±¼ä
                //Èç¹ûgif³ÖÐøÊ±¼äÎªÐ¡ÓÚ100£¬¿ÉÈÏÎª·Çgif×ÊÔ´£¬Ìø³ö´¦Àí
                if(duration > 100){
                    //»ñÈ¡gifµ±Ç°Ö¡µÄÏÔÊ¾ËùÔÚÊ±¼äµã
                    int relTime = (int) ((nowTime - movieStart) % duration);
                    movie.setTime(relTime);
                    //äÖÈ¾gifÍ¼Ïñ
                    movie.draw(canvas, 0, 0);
                    invalidate();
                }
            }
        }
    }
