SET LIBGDX="C:\Development\Java\lib\libgdx-0.9.7"
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 main_menu_screen_atlas_src atlas_out mainmenuscreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 game_screen_atlas_src atlas_out gamescreen
robocopy atlas_out ..\..\Calculate-android\assets\data
