package com.season.rapiddevelopment.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Disc: 两端对齐的text view，可以设置最后一行靠左，靠右，居中对齐
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:22
 */
public class AlignTextView extends TextView {
	private float textHeight; // 单行文字高度
	private float textLineSpaceExtra = 0; // 额外的行间距
	private int width; // textView宽度
	private List<String> lines = new ArrayList<String>(); // 分割后的行
	private List<Integer> tailLines = new ArrayList<Integer>(); // 尾行
	private Align align = Align.ALIGN_LEFT; // 默认最后一行左对齐
	private boolean firstCalc = true; // 初始化计算

	private float lineSpacingMultiplier = 1.0f;
	private float lineSpacingAdd = 0.0f;

	private int originalHeight = 0; // 原始高度
	private int originalLineCount = 0; // 原始行数
	private int originalPaddingBottom = 0; // 原始paddingBottom
	private boolean setPaddingFromMe = false;

	// 尾行对齐方式
	public enum Align {
		ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT // 居中，居左，居右,针对段落最后一行
	}

	public AlignTextView(Context context) {
		super(context);
		//setTextIsSelectable(false);
	}
	
	public int maxLine = Integer.MAX_VALUE;
	
	public void setMaxLine(int line){
		this.maxLine = line;
		invalidate();
	}

	public AlignTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//setTextIsSelectable(false);

		lineSpacingMultiplier = attrs.getAttributeFloatValue("http://schemas.android" + "" + ".com/apk/res/android",
				"lineSpacingMultiplier", 1.0f);

		int[] attributes = new int[] { android.R.attr.lineSpacingExtra };

		TypedArray arr = context.obtainStyledAttributes(attrs, attributes);

		lineSpacingAdd = arr.getDimensionPixelSize(0, 0);

		originalPaddingBottom = getPaddingBottom();

		arr.recycle();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		// 首先进行高度调整
		if (firstCalc) {
			width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
			String text = getText().toString();
			TextPaint paint = getPaint();
			
			lines.clear();
			tailLines.clear();

			// 文本含有换行符时，分割单独处理
			String[] items = text.split("\\n");
			for (String item : items) {
				calc(paint, item, paint.getFontSpacing());
			}

			// 使用替代textview计算原始高度与行数
			measureTextViewHeight(text, paint.getTextSize(), width);

			// 获取行高
			textHeight = 1.0f * originalHeight / originalLineCount;

			textLineSpaceExtra = textHeight * (lineSpacingMultiplier - 1) + lineSpacingAdd;

			// 计算实际高度,加上多出的行的高度(一般是减少)
			int heightGap = (int) ((textLineSpaceExtra + textHeight) * (lines.size() - originalLineCount));

			setPaddingFromMe = true;
			// 调整textview的paddingBottom来缩小底部空白
			setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), originalPaddingBottom + heightGap);

			firstCalc = false;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		TextPaint paint = getPaint();
		paint.setColor(getCurrentTextColor());
		paint.drawableState = getDrawableState(); 

		Paint.FontMetrics fm = paint.getFontMetrics();
		float firstHeight = getTextSize() - (fm.bottom - fm.descent + fm.ascent - fm.top);

		int gravity = getGravity();
		if ((gravity & 0x1000) == 0) { // 是否垂直居中
			firstHeight = firstHeight + (textHeight - firstHeight) / 2;
		}

		int paddingTop = getPaddingTop();
		int paddingLeft = getPaddingLeft();

		for (int i = 0; i < lines.size(); i++) {
			if (i == maxLine) {
				return;
			}
			float drawY = i * textHeight + firstHeight;
			String line = lines.get(i);
			// 绘画起始x坐标
			float drawSpacingX = paddingLeft;
			float gap = (width - paint.measureText(line));
			float interval = gap / (line.length() - 1);

			// 绘制最后一行
			if (tailLines.contains(i)) {
				interval = 0;
				if (align == Align.ALIGN_CENTER) {
					drawSpacingX += gap / 2;
				} else if (align == Align.ALIGN_RIGHT) {
					drawSpacingX += gap;
				}
			}
			float x = paint.measureText("测") + interval ;
			for (int j = 0; j < line.length(); j++) {
				float drawX = paint.measureText(line.substring(0, j)) + interval * j;
				String lineText = line.substring(j, j + 1);
				canvas.drawText(lineText, drawX + drawSpacingX,
						drawY + paddingTop + textLineSpaceExtra * i, paint);
			}
		}
	}
	/**   
	 * 字符串转换成十六进制字符串  
	 * @param str 待转换的ASCII字符串
	 * @return String 每个Byte之间空格分隔，如: [61 6C 6B]  
	 */      
	public static String str2HexStr(String str)    
	{      
	  
	    char[] chars = "0123456789ABCDEF".toCharArray();      
	    StringBuilder sb = new StringBuilder("");    
	    byte[] bs = str.getBytes();      
	    int bit;      
	        
	    for (int i = 0; i < bs.length; i++)    
	    {      
	        bit = (bs[i] & 0x0f0) >> 4;      
	        sb.append(chars[bit]);      
	        bit = bs[i] & 0x0f;      
	        sb.append(chars[bit]);    
	        sb.append(' ');    
	    }      
	    return sb.toString().trim();      
	}   
	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {
	    StringBuffer unicode = new StringBuffer();
	    for (int i = 0; i < string.length(); i++) {
	        // 取出每一个字符
	        char c = string.charAt(i);
	        // 转换为unicode
	        unicode.append("\\u" + Integer.toHexString(c));
	    }
	    return unicode.toString();
	}
	/**  
	 * String的字符串转换成unicode的String  
	 * @param  strText 全角字符串
	 * @return String 每个unicode之间无分隔符  
	 * @throws Exception  
	 */    
	public static String strToUnicode(String strText)    
	    throws Exception    
	{    
	    char c;    
	    StringBuilder str = new StringBuilder();    
	    int intAsc;    
	    String strHex;    
	    for (int i = 0; i < strText.length(); i++)    
	    {    
	        c = strText.charAt(i);    
	        intAsc = (int) c;    
	        strHex = Integer.toHexString(intAsc);    
	        if (intAsc > 128)    
	            str.append("\\u" + strHex);    
	        else // 低位在前面补00    
	            str.append("\\u00" + strHex);    
	    }    
	    return str.toString();    
	}    
	/**
	 * 设置尾行对齐方式
	 *
	 * @param align
	 *            对齐方式
	 */
	public void setAlign(Align align) {
		this.align = align;
		invalidate();
	}

	/**
	 * 计算每行应显示的文本数
	 *
	 * @param text
	 *            要计算的文本
	 */
	private void calc(Paint paint, String text, float fontSpace) {
		if (lines.size() >= maxLine) {
			return;
		}
		if (text.length() == 0) {
			lines.add("\n");
			if (lines.size() >= maxLine) {
				return;
			}
			return;
		}
		int startPosition = 0; // 起始位置
		float oneChineseWidth = paint.measureText("中") + fontSpace;
		int ignoreCalcLength = (int) (width / oneChineseWidth); // 忽略计算的长度
		StringBuilder sb = new StringBuilder(text.substring(0, Math.min(ignoreCalcLength + 1, text.length())));

		for (int i = ignoreCalcLength + 1; i < text.length(); i++) {
			if (paint.measureText(text.substring(startPosition, i + 1)) > width) {
				startPosition = i;
				// 将之前的字符串加入列表中
				if (lines.size() == maxLine - 1) {
					try {
						sb.delete(sb.length() - 3, sb.length());
						sb.append(".");
						sb.append(".");
						sb.append(".");
						lines.add(sb.toString()); 
				        tailLines.add(lines.size() - 1);
					} catch (Exception e) {
						lines.add(sb.toString()); 
				        tailLines.add(lines.size() - 1);
					}
					return;
				}
				lines.add(sb.toString()); 

				sb = new StringBuilder();

				// 添加开始忽略的字符串，长度不足的话直接结束,否则继续
				if ((text.length() - startPosition) > ignoreCalcLength) {
					sb.append(text.substring(startPosition, startPosition + ignoreCalcLength));
				} else {
					if (lines.size() == maxLine - 1) {
						try {
							StringBuffer stringBuffer = new StringBuffer();
							stringBuffer.append(text.substring(startPosition));
							stringBuffer.delete(stringBuffer.length() - 3, stringBuffer.length());
							stringBuffer.append(".");
							stringBuffer.append(".");
							stringBuffer.append(".");
							lines.add(stringBuffer.toString()); 
					        tailLines.add(lines.size() - 1);
						} catch (Exception e) {
							lines.add(text.substring(startPosition)); 
					        tailLines.add(lines.size() - 1);
						}
						return;
					}
					lines.add(text.substring(startPosition)); 
					break;
				}

				i = i + ignoreCalcLength - 1;
			} else {
				sb.append(text.charAt(i));
			}
		}
		if (sb.length() > 0) {
			lines.add(sb.toString()); 
		}

        tailLines.add(lines.size() - 1);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		firstCalc = true;
		super.setText(text, type);
	}
	
	public void recalculate(){
		firstCalc = true;
		requestLayout();
	}
	
	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		if (!setPaddingFromMe) {
			originalPaddingBottom = bottom;
		}
		setPaddingFromMe = false;
		super.setPadding(left, top, right, bottom);
	}

	/**
	 * 获取文本实际所占高度，辅助用于计算行高,行数
	 *
	 * @param text
	 *            文本
	 * @param textSize
	 *            字体大小
	 * @param deviceWidth
	 *            屏幕宽度
	 */
	private void measureTextViewHeight(String text, float textSize, int deviceWidth) {
		TextView textView = new TextView(getContext());
		textView.setText(text);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(deviceWidth, MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		textView.measure(widthMeasureSpec, heightMeasureSpec);
		originalLineCount = textView.getLineCount();
		originalHeight = textView.getMeasuredHeight();
	}
}