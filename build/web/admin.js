// Validate Add Movie Form
function validateAddMovieForm() {
    const movieName = document.getElementById("movieName").value;
    const trailer = document.getElementById("trailer").value;
    const bannerImage = document.getElementById("bannerImage").value;

    if (movieName === "" || trailer === "" || bannerImage === "") {
        alert("All fields are required!");
        return false;
    }
    return true;
}

// Validate Add Now Showing Form
function validateAddNowShowingForm() {
    const movieName = document.getElementById("nowShowingMovieName").value;
    const movieImage = document.getElementById("nowShowingMovieImage").value;

    if (movieName === "" || movieImage === "") {
        alert("All fields are required!");
        return false;
    }
    return true;
}

// Validate Add Upcoming Form
function validateAddUpcomingForm() {
    const movieName = document.getElementById("upcomingMovieName").value;
    const movieImage = document.getElementById("upcomingMovieImage").value;
    const trailerLink = document.getElementById("trailerLink").value;

    if (movieName === "" || movieImage === "" || trailerLink === "") {
        alert("All fields are required!");
        return false;
    }
    return true;
}

// Function to confirm deletion
function confirmDeletion(movieId) {
    const confirmation = confirm("Are you sure you want to delete this item?");
    if (confirmation) {
        // Send a request to delete the movie from the database
        window.location.href = delete?movieId=${movieId};
    }
}