package Canvas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start( Stage primaryStage ) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene( root, 640, 480 );
        MainCanvas mainCanvas = new MainCanvas( 640, 480 );
        root.getChildren().add( mainCanvas );
        scene.getStylesheets().add( getClass().getResource( "application.css" ).toExternalForm() );
        primaryStage.setTitle( "高级战争" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }

    public static void main( String[] args ) { launch( args ); }
}
