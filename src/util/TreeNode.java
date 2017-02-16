package util;

/*
* Binary Tree Node definition - inner class
*/
public class TreeNode {

        private TreeNode left;
        private TreeNode right;
        private String lexval;
        private String type;
        private Token token;

        public TreeNode(){
            this("NIL");
//            this.lexval = "NIL";
//            this.setRight(null);
//            this.setLeft(null);
        }

        public TreeNode(String lexval){
            this.lexval = lexval;
            this.setLeft(null);
            this.setRight(null);
        }

        private void setType(String type){

        }

        public String getLexicalVal(){
            return this.lexval;
        }

        public void setLexicalVal(String lexval){
            if (! (lexval instanceof String)) {
                System.out.println("Error: Failed to set Lexical Value of binary tree node.");
                System.exit(-1);
            }
            this.lexval = lexval;
        }

        @Override
        public String toString(){
            return "<" + (this.isLeaf()? this.getLexicalVal():"Inner Node") + ">";
        }

    public boolean isLeaf() {
        return this.getLeft() == null && this.getRight() == null;
    }

    public TreeNode getLeft() {
        return left;
    }

        public void setLeft(TreeNode node){
            this.left = node;
        }

    public TreeNode getRight() {
        return right;
    }

        public void setRight(TreeNode node){
            this.right = node;
        }
}
