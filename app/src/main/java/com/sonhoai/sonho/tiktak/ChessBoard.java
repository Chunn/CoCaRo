package com.sonhoai.sonho.tiktak;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonho on 3/15/2018.
 */

public class ChessBoard {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private int[][] board;//cac buoc đã đi -1 là chưa đi, 0 la nguoi choi, 1 la may
    private int player;//nguoi choi nào
    private Context context;
    private int bitmapWidth, bitmapHeight, colQty,rowQty;
    private List<Line> lines;

    //gọi nhiều lần
    private Bitmap playerA, playerB;

    public ChessBoard(Context context, int bitmapWidth, int bitmapHeight, int colQty, int rowQty) {
        this.context = context;
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
        this.colQty = colQty;
        this.rowQty = rowQty;
    }

    //lam cac thao tac khoi tao, reset lại giá trị của các phương thức
    public void init(){
        lines = new ArrayList<>();
        bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        board = new int[rowQty][colQty];
        for(int i = 0; i<rowQty; i++){
            for(int j = 0; j < colQty;j++){
                board[i][j] = -1;//-1 là chưa đi
            }
        }

        player = 0;
        paint.setStrokeWidth(2);
        int celWidth = bitmapWidth/colQty;
        int celHeight = bitmapHeight/rowQty;
        for(int i = 0; i <= colQty; i++){
            lines.add(new Line(celWidth*i, 0, celWidth*i, bitmapHeight));
        }
        for(int i = 0; i <= rowQty; i++){
            lines.add(new Line(0, i*celHeight, bitmapWidth, i*celHeight));
        }
    }

    public Bitmap drawBoard(){
        for(int i = 0; i < lines.size(); i++){
            canvas.drawLine(
                    lines.get(i).getX1(),
                    lines.get(i).getY1(),
                    lines.get(i).getX2(),
                    lines.get(i).getY2(),
                    paint
            );
        }
        playerA = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_player_a);
        playerB = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_player_b);

        return bitmap;
    }

    public boolean onTouch(View view, MotionEvent motionEvent){

        int cellWidth = view.getWidth()/colQty;
        int cellHeight = view.getHeight()/rowQty;

        int colIndex = (int) (motionEvent.getX()/cellWidth);
        int rowIndex = (int) (motionEvent.getY()/cellHeight);
        Log.i("Event", colIndex+"-"+rowIndex);

        if(board[rowIndex][colIndex] != -1){
            return true;//co nguoi di roi
        }
        board[rowIndex][colIndex] = player;//gán nước đi là người chơi nào
        int padding = 50;
        if(player == 0){
            canvas.drawBitmap(
                    playerA,
                    new Rect(0,0,playerA.getWidth(), playerA.getHeight()),
                    new Rect(colIndex*cellWidth+padding,rowIndex*cellHeight+padding,(colIndex+1)*cellWidth -padding, (rowIndex+1)*cellHeight -padding),
                    paint);
            player = 1;
        }else {
            canvas.drawBitmap(
                    playerB,
                    new Rect(0,0,playerB.getWidth(), playerB.getHeight()),
                    new Rect(colIndex*cellWidth,rowIndex*cellHeight,(colIndex+1)*cellWidth, (rowIndex+1)*cellHeight),
                    paint);
            player = 0;
        }

        view.invalidate();
//        //cap nhat lai ban cờ
//        player = (player == 0 ? 1 : 0); // (player+1)%2
        return true;
    }

    //    public Bitmap createBitmap(){
//        Bitmap bitmap = Bitmap.createBitmap(line.getBitmapWidth(), line.getBitmapHeight(),Bitmap.Config.ARGB_8888);
//        //đại diện cho chế độ màu(8888, 4444, 565) thuộc class BitmapConfig
//        //dai dien cho to giay
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setStrokeWidth(2);
//        int celHeight = line.getBitmapHeight()/line.getRowQty();
//        for(int i = 0; i <= line.getRowQty(); i++){
//            canvas.drawLine(0,celHeight*i,line.getBitmapHeight(),celHeight*i,paint);
//        }
//        int celWidth = line.getBitmapWidth()/line.getColQty();
//        for(int i = 0; i < line.getColQty(); i++){
//            canvas.drawLine(celWidth*i,0,celWidth*i,line.getBitmapWidth(),paint);
//        }
//        return bitmap;
//    }
//bitmap dai dien cho một bức ảnh
}
