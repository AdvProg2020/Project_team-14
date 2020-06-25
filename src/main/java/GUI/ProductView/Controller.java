package GUI.ProductView;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class Controller {

    public ImageView imageView;
    private static final int MIN_PIXELS = 10;

    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }


    public void initialize() {
        double width = imageView.getFitWidth();
        double height = imageView.getFitHeight();

        imageView.setPreserveRatio(true);
        reset(imageView, width / 2, height / 2);

        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

        imageView.setOnMousePressed(e -> {
            Point2D mousePress = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        imageView.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
            shift(imageView, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(imageView, new Point2D(e.getX(), e.getY())));
        });

        imageView.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = imageView.getViewport();
            double scale = clamp(Math.pow(1.01, delta),
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),
                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())
            );
            Point2D mouse = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;
            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        imageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(imageView, width, height);
            }
        });
    }

}
