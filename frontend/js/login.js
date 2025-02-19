document.getElementById("showRegister").addEventListener("click", function () {
  const container = document.querySelector(".container");
  container.classList.toggle("show-register");
  setTimeout(() => {
    document.getElementById("registerForm").style.transform = "translateX(0)";
  }, 10);
});
