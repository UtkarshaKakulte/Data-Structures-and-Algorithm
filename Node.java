import java.util.Scanner;

class ExpTree {
    static class Exptree {
        char data;
        Exptree left;
        Exptree right;

        Exptree(char data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    static Exptree getnode(char ch) {
        return new Exptree(ch);
    }

    static int precedence(char ch) {
        switch (ch) {
            case '^':
                return 3;
            case '/':
            case '*':
                return 2;
            case '-':
            case '+':
                return 1;
            default:
                return 0;
        }
    }

    static void preOrder(Exptree root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    static void inOrder(Exptree root) {
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.data + " ");
            inOrder(root.right);
        }
    }

    static void postOrder(Exptree root) {
        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.data + " ");
        }
    }

    static Exptree createExpTree(String exp) {
        Exptree[] operst = new Exptree[10];
        Exptree[] treest = new Exptree[10];
        int top1 = -1, top2 = -1;
        Exptree temp, t1, t2, t3;
        char symbol;
        for (int i = 0; i < exp.length(); i++) {
            symbol = exp.charAt(i);
            if (Character.isLetterOrDigit(symbol)) {
                temp = getnode(symbol);
                treest[++top2] = temp;
            } else {
                temp = getnode(symbol);
                if (symbol == '(') {
                    operst[++top1] = temp;
                } else if (top1 == -1 || precedence(operst[top1].data) < precedence(symbol)) {
                    operst[++top1] = temp;
                } else if (symbol == ')') {
                    while (operst[top1].data != '(' && top1 != -1 && top2 != -1 && precedence(operst[top1].data) >= precedence(symbol)) {
                        t3 = operst[top1--];
                        t1 = treest[top2--];
                        t2 = treest[top2--];
                        t3.right = t1;
                        t3.left = t2;
                        treest[++top2] = t3;
                    }
                    if (operst[top1].data == '(')
                        top1--;
                } else {
                    while (top1 != -1 && top2 != -1 && precedence(operst[top1].data) >= precedence(symbol)) {
                        t3 = operst[top1--];
                        t1 = treest[top2--];
                        t2 = treest[top2--];
                        t3.right = t1;
                        t3.left = t2;
                        treest[++top2] = t3;
                    }
                    operst[++top1] = temp;
                }
            }
        }
        while (top1 != -1) {
            t3 = operst[top1--];
            t1 = treest[top2--];
            t2 = treest[top2--];
            t3.right = t1;
            t3.left = t2;
            treest[++top2] = t3;
        }
        return treest[top2];
    }

    public static void main(String[] args) {
        Exptree root;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Infix Expression: ");
        String exp = scanner.nextLine();
        root = createExpTree(exp);
        System.out.print("PreOrder: ");
        preOrder(root);
        System.out.print("\nInOrder: ");
        inOrder(root);
        System.out.print("\nPostOrder: ");
        postOrder(root);
    }
}

