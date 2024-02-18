document.addEventListener("DOMContentLoaded", function()
{
    var homeContent = document.querySelector( '.home-content' );

    homeContent.addEventListener( 'wheel', function( e )
    {
        if ( e.deltaMode == 1 )
        {
            // Multliplier for Firefox
            homeContent.scrollLeft += e.deltaX * 1;
        }
        else
        {
            // Multiplier for other browsers
            homeContent.scrollLeft += e.deltaY * 1;
        }

        e.preventDefault();
    });
});