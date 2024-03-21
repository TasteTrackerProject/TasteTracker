document.addEventListener("DOMContentLoaded", function () {
    const searchForm = document.getElementById('searchForm');
    const searchInput = document.getElementById('search');

    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const inputValue = searchInput.value.trim();

        if (inputValue) {
            const searchTerms = inputValue.split(' ');

            const search = searchTerms[0];
            const second = searchTerms.slice(1).join(' ');

            window.location.href = `/allRestaurant?search=${search}&second=${encodeURIComponent(second)}`; // Przekierowujemy do wygenerowanego URL
        }
    });
});