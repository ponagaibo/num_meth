package lab1;

class ComplexNumber {
    private double Re;
    private double Im;

    ComplexNumber(double re, double im) {
        Re = re;
        Im = im;
    }

    private double getRe() {
        return Re;
    }

    private double getIm() {
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

    static double diff(ComplexNumber prev, ComplexNumber cur) {
        return Math.abs(cur.getRe() - prev.getRe() + cur.getIm() - prev.getIm());
    }

    @Override
    public String toString() {
        return "ComplexNumber{" +
                "Re=" + Re +
                ", Im=" + Im +
                '}';
    }
}
