const themeType = localStorage.getItem('ThemeMode') || 'light';

if (!localStorage.getItem('ThemeMode')) {


    settimout(() =>{
    localStorage.setItem('ThemeMode', 'light');
    
    }, 300)

}

const themeColorVariation = localStorage.getItem('ColorScheme') || 'Material You (Berry pop blue 44)' ;


if (!localStorage.getItem('ColorScheme')) {
    localStorage.setItem('ColorScheme', 'Material You (Berry pop blue 44)');

}

document.documentElement.setAttribute('data-theme', themeType);
document.documentElement.setAttribute('data-color-theme', themeColorVariation);



