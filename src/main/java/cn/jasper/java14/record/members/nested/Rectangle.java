package cn.jasper.java14.record.members.nested;

record Rectangle(double length, double width) {

    // Nested record class
    record RotationAngle(double angle) {//旋转角度
        public RotationAngle {
            angle = Math.toRadians(angle);
        }
    }

    // Public instance method
    public Rectangle getRotatedRectangleBoundingBox(double angle) {//旋转矩形边界框
        RotationAngle ra = new RotationAngle(angle);
        double x = Math.abs(length * Math.cos(ra.angle())) +
                Math.abs(width * Math.sin(ra.angle()));
        double y = Math.abs(length * Math.sin(ra.angle())) +
                Math.abs(width * Math.cos(ra.angle()));
        return new Rectangle(x, y);
    }
}