SET LIBGDX="C:\Development\Java\lib\libgdx-0.9.7"
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 main_menu_screen_atlas_src2 atlas_out2 mainmenuscreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 game_screen_atlas_src2 atlas_out2 gamescreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 stage_intro_screen_atlas_src2 atlas_out2 stageintroscreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 stage_select_screen_atlas_src2 atlas_out2 stageselectscreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 loser_screen_atlas_src2 atlas_out2 loserscreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 winner_screen_atlas_src2 atlas_out2 winnerscreen
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 numberspinner numberspinner numberspinner
robocopy atlas_out2 ..\..\Calculate-android\assets\data /LOG:robocopylog2.txt
robocopy original2 ..\..\Calculate-android\assets\data PumpDemiBoldLET.fnt PumpDemiBoldLET.png /LOG+:robocopylog2.txt
robocopy original2 ..\..\Calculate-android\assets\data ninafont.fnt ninafont.png /LOG+:robocopylog2.txt
robocopy original2 ..\..\Calculate-android\assets\data liberation96.fnt liberation96.png /LOG+:robocopylog2.txt
robocopy original2 ..\..\Calculate-android\assets\data liberation32.fnt liberation32.png /LOG+:robocopylog2.txt
robocopy original2 "C:\Program Files (x86)\Inkscape\share\palettes" calculatepalette1.gpl /LOG+:robocopylog2.txt
robocopy original2 ..\..\Calculate-android\res\drawable-mdpi icon48x48.png /LOG+:robocopylog2.txt
pause