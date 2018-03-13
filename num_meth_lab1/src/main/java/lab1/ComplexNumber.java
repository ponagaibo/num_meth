package lab1;

class ComplexNumber {
    private double Re;
    private double Im;

    ComplexNumber(double re, double im) {
        Re = re;
        Im = im;
    }

    public void setRe(double re) {
        Re = re;
    }

    public void setIm(double im) {
        Im = im;
    }

    public double getRe() {
        return Re;
    }

    public double getIm() {
        return Im;
    }

    void print_complex_number() {
        String str = "" + Re;
        if (Im > 0) {
            str += "+";
        }
        if (Im != 0) {
            str += "" + Im + "i";
        }
        System.out.println(str);
    }
}
