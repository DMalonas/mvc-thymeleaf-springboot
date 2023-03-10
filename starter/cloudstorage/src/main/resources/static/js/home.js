  // Activate the first tab on initialization
    var firstTab = document.querySelector('.nav-link');
    firstTab.classList.add('active');

    // Activate clicked tab and deactivate previously active tabs
    var navTabs = document.querySelectorAll('.nav-link');
    navTabs.forEach(function(tab) {
      tab.addEventListener('click', function(event) {
        event.preventDefault();

        var activeTab = document.querySelector('.nav-link.active');
        activeTab.classList.remove('active');
        this.classList.add('active');

        var target = document.querySelector(this.getAttribute('href'));
        var activeTabContent = document.querySelector('.tab-pane.fade.show.active');
        activeTabContent.classList.remove('show', 'active');
        target.classList.add('show', 'active');
      });
    });