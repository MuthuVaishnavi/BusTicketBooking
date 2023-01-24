package com.busticket;

public class PwdCompare {

		public static int Compare(String[] ans,String pw)
		{
			int len=0,f=0;
			for (int i = 0; i < 10; i++) {
				if (ans[i] != null) {
					len++;
				}
			}
			for (int i = 0; i < len; i++) {
				//System.out.println(ans[i]);
				if (ans[i].equals(pw)) {
					f++;
				}
			}
			return f;
		}

	}
