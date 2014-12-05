invm = {
    /**
     * Sets up this module to be able to properly function.
     * Must be called  before any other function belonging to this module is called.
     * Requires the DOM to be fully loaded.
     */
    init : function() {
        var i = 0;

        // set up top navigation links and corresponding content
        // currently assumes tabs and content in same order
        var navOptions = document.getElementById('nav-list');
        this.navOptions = navOptions.children;
        var tabbedContent = document.getElementById('tabbed-content');
        this.tabbedContent = tabbedContent.children;

        if (this.navOptions.length !== this.tabbedContent.length) {
            console.error('Tabs and content is not mapped one to one');
        }

        for (i=0; i<this.navOptions.length; i++) {
            var self = this;
            (function(_i) {
                self.navOptions[_i].addEventListener('click', function () {
                    self.setActiveNavOption(self.navOptions[_i].id);
                    self.showActiveTabSection(self.tabbedContent[_i].id);
                });
            })(i);
        }

        // initiate sections
        initRentTab();
    },

    initRentTab: function() {
        var inventorySelect = document.getElementById('rent-inventory-select');
        var checkoutSelect = document.getElementById('rent-checkout-select');
        var toCheckout = document.getElementById('rent-to-checkout');
        var toInventory = document.getElementById('rent-to-inventory');

        var self = this;
        toCheckout.addEventListener('click', function() {
            var child = inventorySelect.options[inventorySelect.selectedIndex];
            self.moveOptionToSelect(inventorySelect, checkoutSelect, child);
        });

        toInventory.addEventListener('click', function() {
            var child = checkoutSelect.options[checkoutSelect.selectedIndex];
            self.moveOptionToSelect(checkoutSelect, inventorySelect, child);
        });


        // Using jQuery here to save time
        $("button").click(function() {
            // encapsulate information relevant for renting
            var payload = [];
            var toRent = checkoutSelect.children;
            var i = 0;
            for (var i = 0; i < toRent.length; i++) {
                var patronSelect = document.getElementById('rent-user-select');
                var patronId = patronSelect.options[patronSelect.selectedIndex].dataset.patronId;
                var inventoryId = toRent.dataset.inventoryId;

                payload.push({
                   "patronId" : patronId,
                    "inventoryId" : inventoryId
                });
            }

            $.post('/rent',
                payload,
                function(data,status){
                    alert("Item(s) checked out!");
                });
        });
    },

    /**
     * Updates the style of the navigation menu to show it as being selected.
     *
     * @param id the id of the element that should now be selected
     */
    setActiveNavOption : function(id) {
        this.setActiveOptionDeactivateOthers(id, this.navOptions, 'selected');
    },

    showActiveTabSection: function(id) {
        this.setActiveOptionDeactivateOthers(id, this.tabbedContent, 'visible-content');
    },

    setActiveOptionDeactivateOthers: function(id, elementList, clazz) {
        var i = 0;
        for (i=0; i < elementList.length; i++) {
            var option = elementList[i];
            if (option.id === id) {
                option.classList.add(clazz);
            }
            else {
                option.classList.remove(clazz);
            }
        }
    },

    clearChildren: function(id) {
        var element = document.getElementById(id);
        while (element.lastChild) {
            element.removeChild(element.lastChild);
        }
    },

    moveOptionToSelect: function(source, target, option) {
        target.appendChild(option);
    }

};

invm.init();

