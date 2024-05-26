document.addEventListener("DOMContentLoaded", function() {
    var homeContent = document.querySelector('.home-content');
    var scrollSpeed = 3; // Zwiększona prędkość przewijania

    // Funkcja do kopiowania elementów
    function cloneItems() {
        var items = Array.from(homeContent.children);
        items.forEach(item => {
            var clone = item.cloneNode(true);
            homeContent.appendChild(clone);
        });
    }

    // Początkowe klonowanie elementów
    cloneItems();

    // Funkcja do automatycznego przewijania
    function autoScroll() {
        homeContent.scrollLeft += scrollSpeed;
        // Resetowanie pozycji przewijania, gdy osiągnie połowę długości sklonowanych elementów
        if (homeContent.scrollLeft >= homeContent.scrollWidth / 2) {
            homeContent.scrollLeft = 0;
            // Ponowne klonowanie elementów, aby zapewnić ciągłość
            cloneItems();
        }
    }

    // Ustawienie interwału dla automatycznego przewijania
    var autoScrollInterval = setInterval(autoScroll, 20); // Dostosuj interwał dla płynniejszego lub szybszego przewijania

    // Zatrzymanie przewijania po najechaniu myszką
    homeContent.addEventListener('mouseenter', function() {
        clearInterval(autoScrollInterval);
    });

    // Wznawianie przewijania po opuszczeniu myszą
    homeContent.addEventListener('mouseleave', function() {
        autoScrollInterval = setInterval(autoScroll, 20);
    });
});
