import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

public class DirectoryObject implements Serializable, TreeNode {

    private static final long serialVersionUID = 1L;
    private ArrayList<DirectoryObject> children;
    private DirectoryObject parent;
    
    public DirectoryObject()
    {
        this.children = new ArrayList<DirectoryObject>();
        this.parent = null;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return this.children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return this.children.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        for(int i = 0; i < children.size(); i++)
        {
            if(children.get(i).equals(node))
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Enumeration children() {
        return Collections.enumeration(this.children);
    }
    
    public void setParent(DirectoryObject other)
    {
        this.parent = other;
    }
    
    public void addChild(DirectoryObject other)
    {
        children.add(other);
        other.setParent(this);
    }
    
    public void removeChild(DirectoryObject other)
    {
        children.remove(other);
    }
}
