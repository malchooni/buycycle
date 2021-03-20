<template>
<v-card outlined class="mt-1">
    <v-card-title >{{ title }}</v-card-title>
    <v-card-text>        
        <v-simple-table dense>
            <template v-slot:default>
                <thead>
                <tr>
                    <th class="text-left">COLUMN</th>
                    <th class="text-left">DESC</th>
                    <th class="text-left">DATA TYPE</th>
                    <th class="text-left">SIZE</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in items" :key="item.COLUMN">
                    <td>{{ item.COLUMN }}</td>
                    <td>{{ item.DESC }}</td>
                    <td>{{ item.TYPE }}</td>
                    <td>{{ item.SIZE }}</td>
                </tr>
                </tbody>
            </template>
        </v-simple-table>
    </v-card-text>
</v-card>
</template>

<script>
export default {
    props: ['name', 'items'],
    computed:{
        title: function(){
            return this.name.replace('_repeat', '');
        }
    }
    
}
</script>

<style scoped>

</style>