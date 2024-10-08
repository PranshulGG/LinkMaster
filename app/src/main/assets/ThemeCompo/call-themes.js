
function ThemeTabs(){
    const GetThemeColorScheme = localStorage.getItem('ColorScheme');
    const GetThemeThemeMode = localStorage.getItem('ThemeMode');

    if(GetThemeThemeMode === 'dark'){
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueTabsDark');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleTabsDark');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowTabsDark');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenTabsDark');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedTabsDark');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkTabsDark');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeTabsDark');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalTabsDark');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenTabsDark');
            
        }
    } else{
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueTabsLight');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleTabsLight');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowTabsLight');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenTabsLight');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedTabsLight');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkTabsLight');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeTabsLight');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalTabsLight');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenTabsLight');
            
        }
    }
}


function ThemeRegular(){
    const GetThemeColorScheme = localStorage.getItem('ColorScheme');
    const GetThemeThemeMode = localStorage.getItem('ThemeMode');
    
    if(GetThemeThemeMode === 'dark'){
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueDark');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleDark');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowDark');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenDark');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedDark');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkDark');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeDark');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalDark');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenDark');
            
        }
    } else{
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueLight');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleLight');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowLight');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenLight');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedLight');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkLight');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeLight');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalLight');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenLight');
            
        }
    }
}

function ThemeTopBar(){
    const GetThemeColorScheme = localStorage.getItem('ColorScheme');
    const GetThemeThemeMode = localStorage.getItem('ThemeMode');
    
    if(GetThemeThemeMode === 'dark'){
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueTopBarDark');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleTopBarDark');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowTopBarDark');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenTopBarDark');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedTopBarDark');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkTopBarDark');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeTopBarDark');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalTopBarDark');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenTopBarDark');
            
        }
    } else{
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueTopBarLight');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleTopBarLight');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowTopBarLight');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenTopBarLight');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedTopBarLight');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkTopBarLight');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeTopBarLight');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalTopBarLight');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenTopBarLight');
            
        }
    }
}


function ThemeDialogBottomBar(){
    const GetThemeColorScheme = localStorage.getItem('ColorScheme');
    const GetThemeThemeMode = localStorage.getItem('ThemeMode');
    
    if(GetThemeThemeMode === 'dark'){
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueDarkDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleDarkDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowDarkDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenDarkDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedDarkDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkDarkDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeDarkDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalDarkDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenDarkDialogTabs');
            
        }
    } else{
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueLightDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleLightDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowLightDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenLightDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedLightDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkLightDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeLightDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalLightDialogTabs');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenLightDialogTabs');
            
        }
    }
}


function ThemeDialogRegular(){
    const GetThemeColorScheme = localStorage.getItem('ColorScheme');
    const GetThemeThemeMode = localStorage.getItem('ThemeMode');
    
    if(GetThemeThemeMode === 'dark'){
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueDarkDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleDarkDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowDarkDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenDarkDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedDarkDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkDarkDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeDarkDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalDarkDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenDarkDialogRegular');
            
        }
    } else{
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueLightDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleLightDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowLightDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenLightDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedLightDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkLightDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeLightDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalLightDialogRegular');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenLightDialogRegular');
            
        }
    }
}



function ThemeBothBar(){
    const GetThemeColorScheme = localStorage.getItem('ColorScheme');
    const GetThemeThemeMode = localStorage.getItem('ThemeMode');
    
    if(GetThemeThemeMode === 'dark'){
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueBothBarDark');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleBothBarDark');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowBothBarDark');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenBothBarDark');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedBothBarDark');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkBothBarDark');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeBothBarDark');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalBothBarDark');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenBothBarDark');
            
        }
    } else{
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueBothBarLight');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleBothBarLight');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowBothBarLight');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenBothBarLight');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedBothBarLight');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkBothBarLight');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeBothBarLight');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalBothBarLight');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenBothBarLight');
            
        }
    }
}


function ThemeDialogBothBar(){
    const GetThemeColorScheme = localStorage.getItem('ColorScheme');
    const GetThemeThemeMode = localStorage.getItem('ThemeMode');
    
    if(GetThemeThemeMode === 'dark'){
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueDarkDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleDarkDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowDarkDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenDarkDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedDarkDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkDarkDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeDarkDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalDarkDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenDarkDialogBothBar');
            
        }
    } else{
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueLightDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleLightDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowLightDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenLightDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedLightDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkLightDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeLightDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalLightDialogBothBar');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenLightDialogBothBar');
            
        }
    }
}


function ThemeDialogTopBar(){
    const GetThemeColorScheme = localStorage.getItem('ColorScheme');
    const GetThemeThemeMode = localStorage.getItem('ThemeMode');
    
    if(GetThemeThemeMode === 'dark'){
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueDarkDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleDarkDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowDarkDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenDarkDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedDarkDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkDarkDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeDarkDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalDarkDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenDarkDialogTopBar');
            
        }
    } else{
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueLightDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleLightDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowLightDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenLightDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedLightDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkLightDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeLightDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalLightDialogTopBar');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenLightDialogTopBar');
            
        }
    }
}

function ThemeSheetTopBar(){
    const GetThemeColorScheme = localStorage.getItem('ColorScheme');
    const GetThemeThemeMode = localStorage.getItem('ThemeMode');
    
    if(GetThemeThemeMode === 'dark'){
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueDarkSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleDarkSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowDarkSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenDarkSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedDarkSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkDarkSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeDarkSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalDarkSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenDarkSheetTopBar');
            
        }
    } else{
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueLightSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleLightSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowLightSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenLightSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedLightSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkLightSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeLightSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalLightSheetTopBar');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenLightSheetTopBar');
            
        }
    }
}

function ThemeSheetRegular(){
    const GetThemeColorScheme = localStorage.getItem('ColorScheme');
    const GetThemeThemeMode = localStorage.getItem('ThemeMode');
    
    if(GetThemeThemeMode === 'dark'){
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueDarkSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleDarkSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowDarkSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenDarkSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedDarkSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkDarkSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeDarkSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalDarkSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenDarkSheetRegular');
            
        }
    } else{
        if(GetThemeColorScheme === 'Material You (Berry pop blue 44)'){
            sendThemeToAndroid('BlueLightSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Raisin purple 100)'){
            sendThemeToAndroid('PurpleLightSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Olive pop green 49)'){
            sendThemeToAndroid('YellowLightSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Forest green 33)'){
            sendThemeToAndroid('GreenLightSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Chestnut cool red 122)'){
            sendThemeToAndroid('RedLightSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Raspberry pink P99)'){
            sendThemeToAndroid('PinkLightSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Hint orange 33)'){
            sendThemeToAndroid('OrangeLightSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Deep charcoal 83)'){
            sendThemeToAndroid('CharcoalLightSheetRegular');
        } else if(GetThemeColorScheme === 'Material You (Pine green P40)'){
            sendThemeToAndroid('PineGreenLightSheetRegular');
            
        }
    }
}





function sendThemeToAndroid(theme) {

    AndroidInterface.updateStatusBarColor(theme);

    };