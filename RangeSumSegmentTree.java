package com.john.contest;

public class RangeSumSegmentTree {
	private int[] values;
	private int[] segmentTree;
	private int[] lazy;
	public RangeSumSegmentTree(int[] values){
		this.values = values;
		int n = values.length;
		segmentTree = new int[4*n+6];
		lazy = new int[4*n+6];
		build(0,0,n-1);
	}
	private void build(int root,int l,int h){
		if(l > h) return;
		lazy[root] = 0;
		if(l == h){
			segmentTree[root] = values[l];
			return;
		}
		int mid = (l+h) >> 1;
		build(2*root+1, l, mid);
		build(2*root+2, mid+1, h);
		segmentTree[root] = segmentTree[2*root+1] + segmentTree[2*root+2];
	}
	public void update(int root,int l,int h, int ql,int qh,int addValue){
		if(l > h || ql > qh) return;
		if(lazy[root] != 0){
			segmentTree[root] += lazy[root]*(h-l+1);
			if(l != h){
				lazy[2*root+1] += lazy[root];
				lazy[2*root+2] += lazy[root];
			}
			lazy[root] = 0;
		}
		// no overlap
		if(ql > h || qh < l) return;
		// total overlap
		if(ql <= l && qh >= h){
			segmentTree[root] += addValue*(h-l+1);
			lazy[2*root+1] += addValue;
			lazy[2*root+2] += addValue;
			return;
		}
		// other partial overlap
		int mid = (l+h) >> 1;
		update(2*root+1, l, mid, ql, qh, addValue);
		update(2*root+2, mid+1, h, ql, qh, addValue);
		segmentTree[root] = segmentTree[2*root+1] + segmentTree[2*root+2];
	}
	public int query(int root,int l,int h,int ql,int qh){
		if(l > h || ql > qh) return 0;
		if(lazy[root] != 0){
			segmentTree[root] += lazy[root]*(h-l+1);
			if(l != h){
				lazy[2*root+1] += lazy[root];
				lazy[2*root+2] += lazy[root];
			}
			lazy[root] = 0;
		}
		// no overlap
		if(ql > h || qh < l) return 0;
		// total overlap
		if(ql <= l && qh >= h){
			return segmentTree[root];
		}
		// other partial overlap
		int mid = (l+h) >> 1;
		int left = query(2*root+1, l, mid, ql, qh);
		int right = query(2*root+2, mid+1, h, ql, qh);
		return left + right;
	}
	public static void main(String[] args) {
		RangeSumSegmentTree st = new RangeSumSegmentTree(new int[]{1,3,-2,5,8,0,-5});
		st.update(0, 0, 6, 0, 3, 1);
		st.update(0, 0, 6, 0, 3, 1);
		st.update(0, 0, 6, 0, 3, 1);
		st.update(0, 0, 6, 2, 7, -2);
		System.out.println(st.query(0, 0, 6, 0, 6));
	}
}
