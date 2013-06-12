SET LIBGDX="C:\Development\Java\lib\libgdx-0.9.7"
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 main_menu_screen_atlas_src atlas_out mainmenuscreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 game_screen_atlas_src atlas_out gamescreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 stage_select_screen_atlas_src atlas_out stageselectscreen
robocopy atlas_out ..\..\Calculate-android\assets\data /LOG:robocopylog.txt
robocopy original ..\..\Calculate-android\assets\data PumpDemiBoldLET.fnt PumpDemiBoldLET.png /LOG+:robocopylog.txt
robocopy original "C:\Program Files (x86)\Inkscape\share\palettes" calculatepalette.gpl /LOG+:robocopylog.txt
