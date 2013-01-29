package com.appFactory.calcy;

import java.util.Stack;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorFinalActivity extends Activity implements OnClickListener 
{
	Button buttons[]=null;
	TextView display=null;
	boolean operatorFlag=false;
	Stack<Float>number=new Stack<Float>();
	Stack<String>operator=new Stack<String>();
	public boolean negativeFlag=false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        View v=new View(this);
        display=(TextView)findViewById(R.id.displayDigits);
        display.setText("");
        display.setCursorVisible(true);
        buttons=new Button[20];
        buttons[0]=(Button)findViewById(R.id.button1);        
        buttons[1]=(Button)findViewById(R.id.button2);
        buttons[2]=(Button)findViewById(R.id.button3);
        buttons[3]=(Button)findViewById(R.id.buttonplus);
        buttons[4]=(Button)findViewById(R.id.button4);
        buttons[5]=(Button)findViewById(R.id.button5);
        buttons[6]=(Button)findViewById(R.id.button6);
        buttons[7]=(Button)findViewById(R.id.buttonminus);
        buttons[8]=(Button)findViewById(R.id.button7);
        buttons[9]=(Button)findViewById(R.id.button8);
        buttons[10]=(Button)findViewById(R.id.button9);
        buttons[11]=(Button)findViewById(R.id.buttonmultiply);
        buttons[12]=(Button)findViewById(R.id.buttonClear);
        buttons[13]=(Button)findViewById(R.id.buttonZero);
        buttons[14]=(Button)findViewById(R.id.buttonDecimal);
        buttons[15]=(Button)findViewById(R.id.buttondivide);
        buttons[16]=(Button)findViewById(R.id.buttonAc);
        buttons[17]=(Button)findViewById(R.id.buttonABS);
        buttons[18]=(Button)findViewById(R.id.buttonOFF);        
        buttons[19]=(Button)findViewById(R.id.buttonequal);
        
        for(int i=0;i<buttons.length;i++)
        {
        	buttons[i].setOnClickListener(this);
        }
        
    }
	@Override
	public void onClick(View v)
	{		
			switch(v.getId())
			{
			case R.id.button1:
				display.append("1");
				operatorFlag=false;
				break;
			case R.id.button2:
				display.append("2");
				operatorFlag=false;
				break;
			case R.id.button3:
				display.append("3");
				operatorFlag=false;
				break;
			case R.id.buttonplus:
				if(!operatorFlag && !(display.getText().toString().equals("")))
					display.append("+");
				else
					operatorHandle('+');
				operatorFlag=true;
				break;
			case R.id.button4:
				display.append("4");
				operatorFlag=false;
				break;
			case R.id.button5:
				display.append("5");
				operatorFlag=false;
				break;
			case R.id.button6:
				display.append("6");
				operatorFlag=false;
				break;
			case R.id.buttonminus:
			{
				if(display.getText().toString().equals(""))
				{
					display.setText("-");
				}
				else
				{
					if(!operatorFlag)
						display.append("-");
					else
						operatorHandle('-');
				}
				
				operatorFlag=true;
			}
				break;
			case R.id.button7:
				display.append("7");
				operatorFlag=false;
				break;
			case R.id.button8:
				display.append("8");
				operatorFlag=false;
				break;
			case R.id.button9:
				display.append("9");
				operatorFlag=false;
				break;
			case R.id.buttonmultiply:
				if(!operatorFlag && !(display.getText().toString().equals("")))
					display.append("*");
				else
					operatorHandle('*');
				
				operatorFlag=true;
				break;
			case R.id.buttonClear:
				if(display.getText().toString().length()>0)
				{
					String s=display.getText().toString();
					
					int length=s.length();
					char buff[]=s.toCharArray();
					char ch=buff[length-1];
					if(ch=='+' || ch=='-' || ch=='*' || ch=='/')
						operatorFlag=false;
					display.setText(buff, 0, length-1);
					
					if(display.getText().toString().equals(""))
						operatorFlag=false;
					
				}
				break;
			case R.id.buttonZero:
				display.append("0");
				operatorFlag=false;
				break;
			case R.id.buttonDecimal:
				if(!operatorFlag && !(display.getText().toString().equals("")))				
					display.append(".");
				else
				{
					display.append("0.");
				}
				break;
			case R.id.buttondivide:
				if(!operatorFlag && !(display.getText().toString().equals("")))
					display.append("/");
				else
					operatorHandle('/');
				
				operatorFlag=true;
				break;
			case R.id.buttonAc:
				display.setText("");
				operatorFlag=false;
				break;
			case R.id.buttonABS:
				
				String abs=display.getText().toString();
				String rem=abs.substring(1,abs.length());
				if(!abs.equals(""))
				{
					if(!(rem.contains("+") || rem.contains("-") || rem.contains("*") || rem.contains("/")) )
					{
						Float f=Float.parseFloat(abs);
						int k=f.intValue();
						String ss=f.toString();
						int c=Integer.parseInt(""+ss.charAt(ss.indexOf(".")+1));
						if(c>5)
							k++;		
						
						display.setText(""+k);
						operatorFlag=false;
					}
					else
					{
						Toast.makeText(this,":-( .Can't Make Absolute.",Toast.LENGTH_LONG).show();
					}
				}
					
				break;
			case R.id.buttonOFF:
				finish();
				break;
			case R.id.buttonequal:
				if(!(display.getText().toString().equals(""))   && checkSyntax(display.getText().toString())==1)
				{
					infixEvaluation(display.getText().toString());
					operatorFlag=false;
				}
				else 
					Toast.makeText(getApplicationContext(), "Wrong Syntax...",Toast.LENGTH_LONG).show();
				break;		
			}
}
			
	
	private int checkSyntax(String abs)
	{
		int len=abs.length();
		//Log.d("Calcy","len-1"+abs.charAt(len-1));
		if(abs.charAt(len-1)=='+' || abs.charAt(len-1)=='-' || abs.charAt(len-1)=='*' || abs.charAt(len-1)=='/')
		{
			return 0;
		}
		else
			return 1;
	}
	private void operatorHandle(char opr)
	{
		String ss=display.getText().toString();
		if(ss.equals(""))
		{
			operatorFlag=false;
		}
		else
		{
			if(ss.length()!=1)
			{
				char buff[]=ss.toCharArray();
				char cr=buff[buff.length-1];
				if(opr=='-' && cr!='-' && (cr=='*' || cr=='/'))
				{
					display.append(""+opr);
				}
				else
				{
					Log.d("Calcy","buff "+buff[buff.length-1]+" opr "+opr);
					buff[buff.length-1]=opr;		
					display.setText(buff, 0, buff.length);
				}
			}
		}			
	}
	private void infixEvaluation(String evaluate) 
	{
		//Log.d("Calcy","len "+evaluate.length());
		String toPushNum="";
		for(int i=0;i<evaluate.length();i++)
		{
			char ch=evaluate.charAt(i);
			Log.d("Calcy","firts ch "+ch);
			
			while((ch!='+') && (ch!='-') && (ch!='*') && (ch!='/'))
			{
				Log.d("Calcy","in while ");				
				toPushNum=toPushNum.concat(""+ch);
				Log.d("Calcy","toPush string "+toPushNum);
				i++;
			//	Log.d("Calcy","i "+i);
				if(i<evaluate.length())
				{
					ch=evaluate.charAt(i);
				}
				else
					break;
			}
			if(i==0 && ch=='-')
			{
				toPushNum=""+ch;
				i++;
				ch=evaluate.charAt(i);
				while((ch!='+') && (ch!='-') && (ch!='*') && (ch!='/'))
				{									
					toPushNum=toPushNum.concat(""+ch);
					
					i++;
				
					if(i<evaluate.length())
					{
						ch=evaluate.charAt(i);
					}
					else
						break;
				}
				
			}
			if(ch=='-' && negativeFlag)
			{
				toPushNum=toPushNum.concat(""+ch);
				i++;
				ch=evaluate.charAt(i);
				while((ch!='+') && (ch!='-') && (ch!='*') && (ch!='/'))
				{
									
					toPushNum=toPushNum.concat(""+ch);
					
					i++;
				
					if(i<evaluate.length())
					{
						ch=evaluate.charAt(i);
					}
					else
						break;
				}
				
			}
			
			
			Log.d("Calcy","Pushing number :"+toPushNum);
			number.push(Float.parseFloat(toPushNum));
			toPushNum="";
			if(i>=evaluate.length())
				break;
			
			if(operator.isEmpty())
			{
				operator.push(""+ch);
				if((ch=='*' || ch=='/') && evaluate.charAt(i+1)=='-')
				{
					negativeFlag=true;
				}
				else
				{
					negativeFlag=false;
				}
				Log.d("Calcy","Pushing opr :"+ch);
			}
			else
			{
				if(ch=='*' || ch=='/' && evaluate.charAt(i+1)=='-')
					negativeFlag=true;
				else
					negativeFlag=false;
				
				Log.d("Calcy", precedence(""+ch) +"<="+ precedence(operator.peek()));
				if(precedence(""+ch) <= precedence(operator.peek()) )
				{
					
					String ev=operator.pop();
					Log.d("Calcy","poped operator :"+ev);
					if(!number.isEmpty())
					{
						float f2=number.pop();
						Log.d("Calcy","poped num2 :"+f2);						
						float f1=number.pop();
						Log.d("Calcy","poped num1 :"+f1);
						evalAndPush(f1,ev,f2);
						operator.push(""+ch);
						Log.d("Calcy","Pushing opr :"+ch);
						
					}
				}
				else
				{
					operator.push(""+ch);
					
				}
				
			}
		}
		
		while(!operator.isEmpty())
		{
			String ev=operator.pop();
			float f2=number.pop();
			
				float f1=number.pop();
				evalAndPush(f1,ev,f2);
			
		}
		Float result=number.pop();
			if(result-result.intValue()==0)
			{
				display.setText(""+result.intValue());
			}
			else
			{
				display.setText(result.toString());
			}
		
		
	}
	private void evalAndPush(float f1, String ev, float f2)
	{
		Log.d("Calcy","evalAndPush :"+f1+" "+ev+" "+f2);
		
		
		if(ev.equals("+"))
		{
			number.push(f1+f2);
			Log.d("Calcy","evalAndPush + :"+(f1+f2));
		}
		if(ev.equals("-"))
		{
			number.push(f1-f2);
			Log.d("Calcy","evalAndPush - :"+(f1-f2));
			
		}
		if(ev.equals("*"))
		{
			number.push(f1*f2);
			Log.d("Calcy","evalAndPush * :"+(f1*f2));
		}		
		if(ev.equals("/"))
		{
			number.push(f1/f2);
			Log.d("Calcy","evalAndPush / :"+(f1/f2));
		}
	}
	private int precedence(String ch) //highest the number highest the prio
	{
		if(ch.equals("*") || ch.equals("/"))		
			return 2;
		return 1;
	}
}