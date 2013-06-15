SET LIBGDX="C:\Development\Java\lib\libgdx-0.9.7"
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 main_menu_screen_atlas_src1 atlas_out1 mainmenuscreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 game_screen_atlas_src1 atlas_out1 gamescreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 stage_select_screen_atlas_src1 atlas_out1 stageselectscreen
robocopy atlas_out1 ..\..\Calculate-android\assets\data /LOG:robocopylog1.txt
robocopy original1 ..\..\Calculate-android\assets\data PumpDemiBoldLET.fnt PumpDemiBoldLET.png /LOG+:robocopylog1.txt
robocopy original1 ..\..\Calculate-android\assets\data ninafont.fnt ninafont.png /LOG+:robocopylog1.txt
robocopy original1 "C:\Program Files (x86)\Inkscape\share\palettes" calculatepalette1.gpl /LOG+:robocopylog1.txt
pause