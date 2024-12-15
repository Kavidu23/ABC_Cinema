document.getElementById('menu-icon').addEventListener('click', function () {
    this.classList.toggle('active'); // Toggle the active state
    document.getElementById('side-menu').style.width = document.getElementById('side-menu').style.width === '250px' ? '0' : '250px'; // Toggle the sidebar visibility
});

function searchMovies() {
    const query = document.getElementById('search').value.trim();

    if (query.length > 0) {
        // Send AJAX request to the backend
        fetch(`SearchMoviesServlet?query=${query}`)
            .then(response => response.json())
            .then(data => {
                const resultsDiv = document.getElementById('search-results');
                resultsDiv.innerHTML = ''; // Clear previous results

                // Populate the search results
                data.forEach(movie => {
                    const div = document.createElement('div');
                    div.textContent = movie.movieName; // Use the movieName field
                    div.className = 'search-item';

                    // Add click event to redirect to the booking page
                    div.onclick = () => {
                        // Redirect to the booking page with movieId
                        window.location.href = `book?type=nowshowing&movieId=${movie.id}`;
                    };

                    resultsDiv.appendChild(div);
                });
            })
            .catch(error => console.error('Error fetching search results:', error));
    } else {
        // Clear the results if query is empty
        document.getElementById('search-results').innerHTML = '';
    }
}
