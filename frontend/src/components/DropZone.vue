<template>
	<div :data-active="active" @dragenter.prevent="setActive" @dragover.prevent="setActive" @dragleave.prevent="setInactive" @drop.prevent="onDrop">
		<slot :dropZoneActive="active"></slot>
	</div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue'

export default {
    emits: ['files-dropped'],

    setup() {
        const active = ref(false)
        let inActiveTimeout = null

    function setActive() {
        active.value = true
        clearTimeout(inActiveTimeout)
    }

    function setInactive() {
        inActiveTimeout = setTimeout(() => {
            active.value = false
            }, 50)
    }

    function onDrop(e) {
        setInactive()
        this.emit('files-dropped', [...e.dataTransfer.files])
    }

    function preventDefaults(e) {
        e.preventDefault()
    }

    const events = ['dragenter', 'dragover', 'dragleave', 'drop']

    onMounted(() => {
        events.forEach((eventName) => {
            document.body.addEventListener(eventName, preventDefaults)
        })
    })

    onUnmounted(() => {
        events.forEach((eventName) => {
            document.body.removeEventListener(eventName, preventDefaults)
        })
    })
 
 return {
    active,
    inActiveTimeout,
    setActive,
    setInactive,
    onDrop,
    preventDefaults,
 }
}
}
</script>