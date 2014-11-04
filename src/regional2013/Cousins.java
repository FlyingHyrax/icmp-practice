package regional2013;
import java.util.*;

/**
 * ACM 2013 MidAtl Regional Prob D - "Count Your Cousins"
 * @author mseiler2
 * @version 04-11-2013
 */

public class Cousins {
	
	private static final boolean dbg = false;
	
	public static void main(String[] args) {
		boolean done = false;
		Scanner scin = new Scanner(System.in);
		while (!done) {
			int number = scin.nextInt();
			int focus = scin.nextInt();
			scin.nextLine();
			if (number == 0 && focus == 0) {
				done = true;
			}
			else {
				solveOneTree(number, focus, scin);
			}
		}
		scin.close();
	}
	
	private static void solveOneTree(int number, int focus, Scanner scin) {
		
		LinkedList<Integer> nodeIds = new LinkedList<Integer>();
		for (int i = 0; i < number; i++) {
			nodeIds.add(scin.nextInt());
		}
		scin.nextLine();
		
		FamTree tree = new FamTree(nodeIds, focus);	// create tree
		
		if (dbg) {
			tree.printSelf();	// testing input collection
		}
		
		System.out.println(tree.countNoiCousins());	// count cousins
	}
}

class FamTree {
	TreeNode root;
	TreeNode noi;
	Integer voi;
	
	// this thing has to be populated breadth first - how the heck to do that
	// or am I just massively over-complicating something?
	public FamTree(LinkedList<Integer> vals, int valOfInterest) {
		this.voi = valOfInterest;
		populate((Queue<Integer>) vals);
	}
	
	private void populate(Queue<Integer> vals) {
		Deque<TreeNode> allParents = new LinkedList<TreeNode>();
		TreeNode currentParent = null;
		int prevVal = 0;
		int currentVal = 0;
		
		while (vals.size() > 0) {
			// get the next integer to add to the tree
			currentVal = vals.remove();
			
			// add that sucker to the tree...?
			
			// fresh tree, no root, make root
			if (this.root == null) {
				this.root = new TreeNode(currentVal, null);
				allParents.add(this.root);
			}
			// current value belongs to the current parent
			else if (currentVal == prevVal + 1) {
				currentParent = allParents.getFirst();	// current parent is head of queue
				allParents.add(addNode(currentVal, currentParent));
				
			}
			// current value belongs to the next available parent
			else {
				// find the next parent with no children... only needed b/c root special case?
				if (allParents.getFirst().getChildren().size() == 0) {
					currentParent = allParents.getFirst();
				}
				else {
					allParents.remove();
					currentParent = allParents.getFirst();
				}
				allParents.add(addNode(currentVal, currentParent));
			}
			
			// prepare to iterate - store previous value 
			prevVal = currentVal;
		}
	}
	
	private TreeNode addNode(int value, TreeNode parent) {
		TreeNode temp = new TreeNode(value, parent);
		parent.addChild(temp);
		if (value == this.voi) {
			this.noi = temp;
		}
		return temp;
	}
	
	public void printSelf() {
		printSelf(this.root);
	}
	
	private void printSelf(TreeNode parent) {
		for (TreeNode child : parent.getChildren()) {
			System.out.println("[par " + parent.getId() + " has child " + child.getId() + "]");
			printSelf(child);
		}
	}
	
	public int countNoiCousins() {
		int count = 0;
		// find grandparent  (check nulls?)
		TreeNode grandparent = getGramps(this.noi);
		if (grandparent != null) {
			// delete parent
			deleteSubtree(this.noi.getParent());
			// count grandparent's children's children
			for (TreeNode child : grandparent.getChildren()) {
				count += child.getChildren().size();
			}
		}
		return count;
	}
	
	private void deleteSubtree(TreeNode node) {
		node.getParent().getChildren().remove(node);
	}
	
	private TreeNode getGramps(TreeNode node) {
		if (node != null) {	// why does this outermost 'if' even work?
			if (node.getParent() != null) {
				if (node.getParent().getParent() != null) {
					return node.getParent().getParent();
				}
			}
		}
		return null;
	}
	
}

class TreeNode {
	private int id;
	private TreeNode parent;
	private List<TreeNode> children;
	
	public TreeNode(int value, TreeNode parent) {
		this.id = value;
		this.parent = parent;
		this.children = new LinkedList<TreeNode>();
	}
	
	public void addChild(TreeNode child) {
		this.children.add(child);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		else if (this.getClass() != other.getClass()) {
			return false;
		}
		else if (this == other) {
			return true;
		}
		else {
			TreeNode otherNode = (TreeNode) other;
			return (this.id == otherNode.getId() &&
					this.children.equals(otherNode.getChildren()) &&
					this.parent.equals(otherNode.getParent()));
		}
	}
	
	// weak tea.  for this one problem, the id will be unique.  Not so always?
	public int hashCode() {
		return this.id;
	}
}
